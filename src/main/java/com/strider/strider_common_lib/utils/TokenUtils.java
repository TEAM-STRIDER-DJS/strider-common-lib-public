package com.strider.strider_common_lib.utils;

import com.strider.strider_common_lib.error.StriderErrorCodes;
import com.strider.strider_common_lib.exception.StriderException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;


@Component
@Slf4j
public class TokenUtils {
    private final Key accessSigningKey;

    public TokenUtils(@Value("${jwt.access-secret-key}") String secret) {
        this.accessSigningKey = Keys.hmacShaKeyFor(secret.getBytes());

        // TODO: 추후 발급/검증 코드 모두에 StandardCharsets.UTF_8을 넣는게 좋음
//        this.accessSigningKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        // Prevent instantiation
    }

    public String getUidFrom(HttpHeaders headers) {
        String accessToken = extractToken(headers);

        if(accessToken == null) throw new StriderException(StriderErrorCodes.UNAUTHORIZED);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(accessSigningKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            return claims.get("uid", String.class);
        } catch (ExpiredJwtException e) {
            System.out.println("case1 err: " + e);
            throw new StriderException(StriderErrorCodes.UNAUTHORIZED); // 토큰 만료
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("case2 err: " + e);
            throw new StriderException(StriderErrorCodes.UNAUTHORIZED); // 토큰 위조/깨짐
        } catch (Exception e) {
            System.out.println("case3 err: " + e);
            throw new StriderException(StriderErrorCodes.NOT_FOUND);
        }

//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .build()
//                    .parseClaimsJws(accessToken)
//                    .getBody();
//
//            System.out.print(claims.get("uid", String.class));
//            return claims.get("uid", String.class);
//        } catch (Exception e) {
//            throw new StriderException(StriderErrorCodes.NOT_FOUND);
//        }
    }

    private String extractToken(HttpHeaders headers) {
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}