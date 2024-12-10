package com.msa.user.application.service;


import com.msa.user.domain.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_USER_ROLE = "role";
    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;
    @Value("${jwt.expired-time}")
    private Long TOKEN_EXPIRATION_TIME;

    public String generateToken(String userId, Role role) {
        final Date now = new Date();
        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME));
        claims.put(CLAIM_USER_ID, userId);
        claims.put(CLAIM_USER_ROLE, role);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] encodedKey = Base64.getEncoder().encode(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(encodedKey);
    }

}
