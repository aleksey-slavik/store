package com.globallogic.store.security.jwt;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import com.globallogic.store.security.spring.AuthenticatedUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.user.id}")
    private String userIdKey;

    @Value("${jwt.user.role}")
    private String userRoleKey;

    private Clock clock = DefaultClock.INSTANCE;

    public Long getIdFromToken(String token) {
        return Long.parseLong((String) getClaimsFromToken(token).get(userIdKey));
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Role getRoleFromToken(String token) {
        return Role.valueOf((String) getClaimsFromToken(token).get(userRoleKey));
    }

    public Date getIssuedAtFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt();
    }

    public Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(clock.now());
    }

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            User user = new User();
            user.setId(Long.parseLong((String) body.get(userIdKey)));
            user.setUsername(body.getSubject());
            user.setRole(Role.valueOf((String) body.get(userRoleKey)));
            return user;
        } catch (JwtException e) {
            return null;
        }
    }

    public String generateToken(UserDetails user) {
        Date created = clock.now();
        Date expired = calculateExpiration(created);

        Claims claims = Jwts.claims();
        claims.put(userRoleKey, user.getAuthorities());
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
        return username.equals(user.getUsername()) && isTokenExpired(token);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date calculateExpiration(Date created) {
        return new Date(created.getTime() + expiration * 1000);
    }

    public static void main(String[] args) {
        SecretKey key = MacProvider.generateKey();
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
        System.out.println(UUID.randomUUID().toString());
    }
}
