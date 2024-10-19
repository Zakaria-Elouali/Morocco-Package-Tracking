package com.morocco.mpt.Controller.authentication.jwt;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morocco.mpt.Controller.authentication.exceptions.InactiveUserException;
import com.morocco.mpt.domain.BaseEntity;
import com.morocco.mpt.domain.users.Users;
import com.morocco.mpt.service.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    String AUTHENTICATION_URL = "/login";

    private final AuthenticationManager authenticationManager;

    private final JWTHelper jwtHelper;
    private final UserService userService;


    @PostConstruct
    public void init () {
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl(AUTHENTICATION_URL);
    }

    // warning   i need to figerout how to use email and username on login in the userLouder class to
    @Override
    public Authentication attemptAuthentication (HttpServletRequest request,
                                                 HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        username = username != null ? username.trim() : "";

        password = password != null ? password : "";

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authentication)
            throws IOException, InactiveUserException {

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        String authorizationResponse = createAuthorizationResponse(username);
        PrintWriter writer = response.getWriter();
        writer.write(authorizationResponse);
        writer.flush();
    }
    /**
     * Creates the authorization response containing the JWT and user details.
     *
     * @param username The username of the authenticated user.
     * @return A string representation of the authorization result in JSON format.
     */
    private String createAuthorizationResponse (String username) throws InactiveUserException{
        Users user = userService.getUser(username);

        if (user.getStatusCode() == null || user.getStatusCode() == BaseEntity.StatusCodes.INACTIVE) {
            throw new InactiveUserException("User is inactive and cannot be authenticated.");
        }

        String token = jwtHelper.generateToken(username);

        AuthorizationDetails authorizationDetails = new AuthorizationDetails(token,
                user.getFirstname(),
                user.getSecondname(),
                user.getRole(),
                user.getUsername());
        return convertToJsonString(authorizationDetails);
    }

    /**
     * Converts the authorization details object to a JSON string.
     *
     * @param result The authorization details to be converted.
     * @return A JSON string representation of the authorization details.
     */
    @SneakyThrows
    private String convertToJsonString (AuthorizationDetails result) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

        return objectMapper.writeValueAsString(result);
    }

    /**
     * A record that holds the details to be included in the authorization response.
     */
    private record AuthorizationDetails(String token, String firstname, String lastname, Users.Role role, String username) {}
}
