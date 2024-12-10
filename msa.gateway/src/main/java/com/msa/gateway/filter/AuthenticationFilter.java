package com.msa.gateway.filter;


import com.msa.gateway.util.JwtUtil;
import com.msa.gateway.util.JwtValidationType;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;
    private final static String TOKEN_PREFIX = "Bearer ";
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_USER_ROLE = "role";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }
        final String token = getJwtFromRequest(exchange);

        if (token == null || jwtUtil.getTokenValidate(token)!=JwtValidationType.VALID_TOKEN) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        final Claims claims = jwtUtil.getClaims(token);
        String userid = (String) claims.get(CLAIM_USER_ID);
        String username = (String) claims.get(CLAIM_USER_ROLE);

        exchange.getRequest().mutate()
                .header("X-User-Id", userid)
                .header("X-User-Role", username)
                .build();

        return chain.filter(exchange);
    }

    private String getJwtFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
            return authHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
