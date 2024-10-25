package com.demo.policy_service.Util;

import com.demo.policy_service.Constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class HeaderUtils {

  private static final  Logger logger = LoggerFactory.getLogger(HeaderUtils.class);

        private Key getSigningKey(){
            byte[] keyBytes = Decoders.BASE64.decode(AppConstants.SECRET);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public List<String> extractRoles(String token) {
            Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
            logger.info("Roles for the logged in user is : {}",claims.get("roles"));
            return claims.get("roles", List.class);
        }

        public String extractUserName(String token){
            Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
            logger.info("Logged in user name is : ",claims.getSubject());
            return claims.getSubject();
        }

    public String getToken(HttpHeaders headers) {
            String authToken = headers.get(HttpHeaders.AUTHORIZATION).get(0);
            if(authToken != null && authToken.startsWith("Bearer ")){
                authToken = authToken.substring(7).trim();
                return authToken;
            }
           return null;
    }
}
