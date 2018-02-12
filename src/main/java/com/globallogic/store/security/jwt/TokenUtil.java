package com.globallogic.store.security.jwt;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import com.globallogic.store.security.AuthenticatedUser;
import com.globallogic.store.security.AuthenticatedUserFactory;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

public class TokenUtil {

    /**
     * Secret key for generate and parse tokens
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Expiration value in milliseconds
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Key for access to user id
     */
    @Value("${jwt.user.id}")
    private String userIdKey;

    /**
     * Key for access to user authority
     */
    @Value("${jwt.user.role}")
    private String userRoleKey;

    @Value("jwt.user.credentials")
    private String userCredentialsKey;

    /**
     * {@link Clock} object for create and validate {@link Date} values
     */
    private Clock clock = DefaultClock.INSTANCE;

    /**
     * Parse user id from given token
     *
     * @param token given token
     * @return user id
     */
    public Long getIdFromToken(String token) {
        return ((Number) getClaimsFromToken(token).get(userIdKey)).longValue();
    }

    /**
     * Parse username from given token
     *
     * @param token given token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Parse authority from given token
     *
     * @param token given token
     * @return user authority
     */
    public GrantedAuthority getRoleFromToken(String token) {
        return new SimpleGrantedAuthority((String) getClaimsFromToken(token).get(userRoleKey));
    }

    /**
     * Parse credentials from given token
     *
     * @param token given token
     * @return user credentials
     */
    public String getCredentialsFromToken(String token) {
        return (String) getClaimsFromToken(token).get(userCredentialsKey);
    }

    /**
     * Parse {@link Date} which consist date when token was created
     *
     * @param token given token
     * @return created {@link Date}
     */
    public Date getIssuedAtFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt();
    }

    /**
     * Parse {@link Date} which consist date bigger then token is not valid
     *
     * @param token given token
     * @return expired {@link Date}
     */
    public Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public AuthenticatedUser parseToken(String token) {
        try {
            return new AuthenticatedUser(
                    getIdFromToken(token),
                    getUsernameFromToken(token),
                    getCredentialsFromToken(token),
                    getRoleFromToken(token),
                    true
            );
        } catch (JwtException e) {
            return null;
        }
    }

    public String generateToken(AuthenticatedUser user) {
        Date created = clock.now();
        Date expired = calculateExpiration(created);

        Claims claims = Jwts.claims();
        claims.put(userIdKey, user.getId());
        claims.put(userRoleKey, user.getAuthority().getAuthority());
        claims.put(userCredentialsKey, user.getPassword());
        claims.setSubject(user.getUsername());
        claims.setIssuedAt(created);
        claims.setExpiration(expired);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String refreshToken(String token) {
        Date created = clock.now();
        Date expired = calculateExpiration(created);
        Claims claims = getClaimsFromToken(token);
        claims.setIssuedAt(created);
        claims.setExpiration(expired);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token, AuthenticatedUser user) {
        String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date calculateExpiration(Date created) {
        return new Date(created.getTime() + expiration);
    }

    /**
     * Check expiration of given token
     *
     * @param token given token
     * @return true if token is valid, false in otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(clock.now());
    }
}
