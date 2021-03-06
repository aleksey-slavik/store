package com.globallogic.store.security.jwt;

import com.globallogic.store.security.core.AuthenticatedUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Java web token utility class
 *
 * @author oleksii.slavik
 */
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

    /**
     * Key for access to user permission
     */
    @Value("${jwt.user.permission}")
    private String userPermissionKey;

    /**
     * Key to access to user credentials
     */
    @Value("&{jwt.user.password}")
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
    public Collection<? extends GrantedAuthority> getRoleFromToken(String token) {
        String[] authorityNames = ((String) getClaimsFromToken(token).get(userRoleKey)).split(",");
        return Arrays.stream(authorityNames).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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

    /**
     * Decrypt user data from token
     *
     * @param token given token
     * @return decrypted user
     */
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

    /**
     * Encrypt user data to token
     *
     * @param user given user data
     * @return encrypted token
     */
    public String generateToken(AuthenticatedUser user) {
        Date created = clock.now();
        Date expired = calculateExpiration(created);

        Claims claims = Jwts.claims();
        claims.put(userIdKey, user.getId());
        claims.put(userRoleKey, createAuthorityList(user.getAuthorities()));
        claims.put(userCredentialsKey, user.getPassword());
        claims.setSubject(user.getUsername());
        claims.setIssuedAt(created);
        claims.setExpiration(expired);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Refresh token expiration date
     *
     * @param token given token
     * @return token with refreshed date
     */
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

    /**
     * Check valid token or not
     *
     * @param token given token
     * @param user given user data
     * @return true if token is valid, else in otherwise
     */
    public boolean validateToken(String token, AuthenticatedUser user) {
        String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Get claims from token
     *
     * @see Claims
     * @param token given token
     * @return token claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Calculate expiration date for given date
     *
     * @param created created date
     * @return expiration date
     */
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

    /**
     * Create user authorities list as string for encrypt/decrypt their in token
     *
     * @param authorities authority list
     * @return string representation of authorities
     */
    private String createAuthorityList(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder builder = new StringBuilder();

        for (GrantedAuthority authority : authorities) {
            builder.append(authority.getAuthority());
            builder.append(",");
        }

        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }
}
