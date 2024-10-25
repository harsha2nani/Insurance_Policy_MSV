package com.demo.gateway_service.Filter;

import com.demo.gateway_service.Util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthFilter(){
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange,chain)-> {
            if(validator.isSecured.test(exchange.getRequest())){
                // check whether token present or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7).trim();
                }
                try{
                    jwtUtil.validateToken(authHeader);
                    List<String> roles = jwtUtil.extractRoles(authHeader);
                    if(isRestrictedEndPoint(exchange.getRequest().getURI().getPath(),roles)){
                        return handleUnauthorized(exchange, "Access Denied: Insufficient permissions");
                    }

                }catch(Exception e ){
                    return handleUnauthorized(exchange,"UnAuthorized Access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public boolean isRestrictedEndPoint(String path,List<String> roles){
     List<String> adminApis = Arrays.asList(
             "/policy"
     );

     List<String> userApis = Arrays.asList(
             "/policy/getInfo","/policy/getItemByCategory"
     );

     for(String adminUri : adminApis){
         if(path.startsWith(adminUri)){
             return !roles.contains("ROLE_ADMIN");
         }
     }
     for(String userUri:userApis){
         if(path.startsWith(userUri)){
             return !roles.contains("ROLE_USER");
         }
     }
     return false;

    }

    public Mono<Void> handleUnauthorized(ServerWebExchange exchange, String message){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(
                String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8)
        )));
    }

    public static class Config{

    }
}
