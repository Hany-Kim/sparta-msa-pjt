package com.spring_cloud.eureka.client.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LocalJwtAuthenticationFilter implements GlobalFilter {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = String.valueOf(exchange.getRequest().getMethod());

        if(path.equals("/auth/sign-in") || path.equals("/auth/sign-up") || path.equals("/user/{username}")){
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);

        if(token == null || !validateToken(token, path, method)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    private String extractToken(ServerWebExchange exchange){
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token, String path, String method) {
        try {
            log.info("Received token: {}", token);

            // 키 생성 및 JWT 파싱
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            log.info("Parsed JWT Body: {}", claimsJws.getBody());
            log.info("Request Path: {}, Method: {}", path, method);

            // 추가 검증 로직
            if (path.equals("/products") && method.equals("POST")) {
                if (!claimsJws.getBody().get("role").equals("OWNER")
                        && !claimsJws.getBody().get("role").equals("MANAGER")
                        && !claimsJws.getBody().get("role").equals("MASTER")) {
                    log.warn("Unauthorized role for /products POST: {}", claimsJws.getBody().get("role"));
                    return false;
                }
            } else if (path.equals("/orders") && (method.equals("PUT") || method.equals("POST"))) {
                if (!claimsJws.getBody().get("role").equals("CUSTOMER")) {
                    log.warn("Unauthorized role for /orders: {}", claimsJws.getBody().get("role"));
                    return false;
                }
            }

            log.info("Token validated successfully for path: {}, method: {}", path, method);
            return true;

        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

}
