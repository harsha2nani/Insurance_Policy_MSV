package com.demo.gateway_service.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.List;


@Component
public class JwtUtil {
    public static final String SECRET = "5YSdMcIQf4oN4rvl/9lwYWsg72xLqI2PY2dPmbLbhFDYIPXUgJBMNqpeqQZfMgWs";

    public void validateToken(final String token){
        Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public List<String> extractRoles(String token) {
        // Logic to extract roles from the token claims
        Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
        System.out.println(claims.get("roles"));
        System.out.println(claims.getAudience());
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());

        return claims.get("roles",List.class);
    }
}
