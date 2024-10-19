package com.morocco.mpt.Controller.authentication.jwt;

import com.morocco.mpt.Controller.authentication.exceptions.InvalidAuthorizationException;
import com.morocco.mpt.Controller.authentication.exceptions.InvalidJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
@RequiredArgsConstructor
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login") || request.getServletPath().equals("/register");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            processToken(request);
            filterChain.doFilter(request, response);
        } catch (InvalidJwtException e) {
            unauthorized(response, e, "Invalid token. Cause: " + e.getCause().getMessage());
        } catch (InvalidAuthorizationException e) {
            unauthorized(response, e, "Authorization header is missing or invalid.");
            response.setHeader("WWW-Authenticate", "Bearer realm=\"MOROCCO-PT.com\"");
        } catch (UsernameNotFoundException e) {
            unauthorized(response, e, "Authentication failed: User not found.");
        }
    }

    /**
     * Sends a response with a 401 Unauthorized status code and a custom error message.
     *
     * @param response HTTP response.
     * @param e        Cause of the unauthorized status.
     * @param message  Custom error message.
     */
    private void unauthorized(HttpServletResponse response, Exception e, String message)
            throws IOException {
        logger.debug(message, e);
        response.setStatus(SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    /**
     * Extracts the token from the Authorization header and performs validations.
     *
     * @param request Incoming HTTP request.
     */
    private void processToken(HttpServletRequest request)
            throws InvalidJwtException, InvalidAuthorizationException {
        String token = getToken(request);
        jwtHelper.validate(token);

        String username = jwtHelper.getUsername(token);
        setAuthentication(request, username);
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * @param request Incoming HTTP request.
     * @return Extracted JWT token.
     */
    private String getToken(HttpServletRequest request) throws InvalidAuthorizationException {
        String requestHeader = request.getHeader("Authorization");

        if (requestHeader == null || !requestHeader.startsWith(BEARER_PREFIX)) {
            logger.debug("Invalid Request Header");
            throw new InvalidAuthorizationException("Authorization Header missing or invalid");
        }

        return requestHeader.substring(BEARER_PREFIX_LENGTH);
    }

    /**
     * Sets the authentication in the security context if the user is not already authenticated.
     *
     * @param request  Incoming HTTP request
     * @param username Username extracted from token
     */
    private void setAuthentication(HttpServletRequest request, String username) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (securityContext.getAuthentication() != null) {
            logger.info("User '%s' already authenticated".formatted(securityContext.getAuthentication()
                    .getName()));
            return;
        }

        UserDetails userDetails = getUserDetails(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        securityContext.setAuthentication(authentication);
    }

    /**
     * Retrieves user details based on the username.
     *
     * @param username Username extracted from token
     * @return Optional containing user details if present
     */
    private UserDetails getUserDetails(String username) {
        try {
            return this.userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            logger.warn("Could not find user '%s', but has well formed Token".formatted(username));
            throw e;
        }
    }

}
