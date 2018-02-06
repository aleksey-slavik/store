package com.globallogic.store.security.jwt;

import com.globallogic.store.model.Role;
import com.globallogic.store.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Long id = Long.parseLong((String) body.get("userId"));
            String username = body.getSubject();
            Role role = Role.valueOf((String) body.get("userRole"));
            return new User(id, username, role);
        } catch (JwtException | ClassCastException e) {
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
