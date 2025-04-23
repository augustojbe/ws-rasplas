package com.augustojbe.client.service.impl;

import com.augustojbe.client.model.UserCredential;
import com.augustojbe.client.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;


    @Override
    public String getToken(Authentication auth) {
        UserCredential user = (UserCredential) auth.getPrincipal();

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + 1000 * 60 * 60 * 10);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .setIssuer("API Rasmoo Plus")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean isValid(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Logar a exceção para diagnóstico
            System.out.println("Erro ao validar token: " + ex.getMessage());
            return false;
        }
    }


    @Override
    public Long getUserId(String token) {
        Jws<Claims> claims = getClaimsJws(token);
        return Long.parseLong(claims.getBody().getSubject());

    }

    private Jws<Claims> getClaimsJws(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

}
