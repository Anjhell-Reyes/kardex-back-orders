package com.kardex.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;

@Service
public class JwtService {

    @Value("${secret.key}")
    private String secretKey;

    public String extractUserId(String token) {
        return extractClaim(token, "id");
    }

    public String extractCompanyName(String token) {
        return extractClaim(token, "companyName");
    }

    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    private String extractClaim(String token, String claimKey) {
        return extractAllClaims(token).get(claimKey, String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
