package com.example.blog.security;

import com.example.blog.exception.JwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String SECRET;

    @Value("${app.jwt.expires}")
    private long EXPIRES;

    // generate sign key
    private Key generateKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    // generate token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date expires = new Date(new Date().getTime() + EXPIRES);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expires)
                .signWith(generateKey())
                .compact();
    }

    // get username from token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())
                    .build()
                    .parse(token);

            return true;
        } catch (MalformedJwtException e) {
            throw new JwtException("Invalid token");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Expired token");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported token");
        } catch (IllegalArgumentException e) {
            throw new JwtException("Abnormal token");
        }
    }
}
