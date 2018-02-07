package com.globallogic.store.security.jwt;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.user.id}")
    private String userIdKey;

    @Value("${jwt.user.role}")
    private String userRoleKey;

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

    public String generateToken(User user) {
        Claims body = Jwts.claims().setSubject(user.getUsername());
        body.put(userIdKey, user.getId());
        body.put(userRoleKey, user.getRole().name());

        return Jwts.builder().setClaims(body).signWith(SignatureAlgorithm.ES512, secret).compact();
    }
}
