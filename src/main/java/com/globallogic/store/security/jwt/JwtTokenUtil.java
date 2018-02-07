package com.globallogic.store.security.jwt;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            User user = new User();
            user.setId(Long.parseLong((String) body.get("userId")));
            user.setUsername(body.getSubject());
            user.setRole(Role.valueOf((String) body.get("userRole")));
            return user;
        } catch (JwtException e) {
            return null;
        }
    }

    public String generateToken(User user) {
        Claims body = Jwts.claims().setSubject(user.getUsername());
        body.put("userId", user.getId());
        body.put("userRole", user.getRole().name());

        return Jwts.builder().setClaims(body).signWith(SignatureAlgorithm.ES512, secret).compact();
    }
}
