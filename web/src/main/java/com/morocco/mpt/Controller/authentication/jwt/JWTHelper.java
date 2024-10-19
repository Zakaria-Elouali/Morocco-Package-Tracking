package com.morocco.mpt.Controller.authentication.jwt;


import com.morocco.mpt.Controller.authentication.exceptions.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTHelper {

    private final String secretKey;

    private static final long JWT_TOKEN_VALIDITY = 12 * 60 * 60 * 1000;
    private final SecretKey skey;

    public JWTHelper() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
             skey =keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(skey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] encodedKey = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(encodedKey) ;
    }

    /**
     * Retrieves the username (subject) from the provided JWT token.
     *
     * @param token JWT token string.
     * @return Username as a string.
     * @throws InvalidJwtException when there is an issue with the JWT token.
     */
    String getUsername(String token) throws InvalidJwtException {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Retrieves the expiration date of the provided JWT token.
     *
     * @param token JWT token string.
     * @return Expiration date as a Date object.
     * @throws InvalidJwtException when there is an issue with the JWT token.
     */
    Date getExpirationDate(String token) throws InvalidJwtException {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Generic method to extract a specific claim from the JWT token.
     *
     * @param token          JWT token string.
     * @param claimsResolver Function to extract the desired claim.
     * @return the desired claim.
     */
    <T> T getClaim(String token, Function<Claims, T> claimsResolver) throws InvalidJwtException {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the header from the provided JWT token.
     *
     * @param token JWT token string.
     * @return All claims as a `Claims` object.
     */
    private JwsHeader getHeader(String token) throws InvalidJwtException {
        JwtParser parser = Jwts.parser().verifyWith(skey).build();
        try {
            return parser.parseSignedClaims(token).getHeader();
        } catch (Exception e) {
            throw new InvalidJwtException(e);
        }
    }

    /**
     * Retrieves all claims from the provided JWT token.
     *
     * @param token JWT token string.
     * @return All claims as a `Claims` object.
     */
    private Claims getAllClaims(String token) throws InvalidJwtException {
        JwtParser parser = Jwts.parser().verifyWith(skey).build();
        try {
            return parser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new InvalidJwtException(e);
        }
    }

    void validate(String token) throws InvalidJwtException {
        if (expired(token)) {
            JwsHeader header = getHeader(token);
            Claims claims = getAllClaims(token);
            throw new InvalidJwtException(new ExpiredJwtException(header, claims, "JWT Expired"));
        }
    }

    /**
     * Checks if the provided JWT token has expired.
     *
     * @param token JWT token string.
     * @return true if the token has expired, false otherwise.
     */
    private boolean expired(String token) throws InvalidJwtException {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }
}
