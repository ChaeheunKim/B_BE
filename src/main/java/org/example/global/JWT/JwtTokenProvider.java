package org.example.global.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.domain.user.UserEntity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class JwtTokenProvider {
    private final String secretKey;
    private final long JwtValidityHours;

    @Autowired
    public JwtTokenProvider(
            @Value("${secret}") String secretKey,
            @Value("${validityHours}") long JwtValidityHours) {
        this.secretKey = secretKey;
        this.JwtValidityHours = JwtValidityHours;
    }


    public String createToken(String email, Role role) {
        Claims claims = Jwts.claims()
                .setSubject(email);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims) // JWT 토큰에 담는 클레임(유저 정보)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) //JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(JwtValidityHours, ChronoUnit.HOURS))) //JWT 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) //시크릿 키로 서명
                .compact();
    }



    public String getSubjectFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }





    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
