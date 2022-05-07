package me.weekbelt.securityplayground.apiserver.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.securityplayground.apiserver.auth.dto.TokenDto;
import me.weekbelt.securityplayground.persistence.auth.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private static final String SECRET_KEY = "jwt_security_secret_key_password";

    @Value("${jwt.access-token.expiration:600000}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration:6000000}")
    private long refreshTokenExpiration;

    public TokenDto createTokenDto(Member member, List<String> roles) {
        return TokenDto.builder()
            .accessToken(this.createAccessToken(member, roles))
            .refreshToken(this.createRefreshToken(member))
            .type("JWT")
            .build();
    }

    public String createAccessToken(Member member, List<String> roles) {
        return Jwts.builder()
            .setHeader(this.getHeader())
            .setSubject(String.valueOf(member.getId()))
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
            .claim("username", member.getUsername())
            .claim("roles", roles)
            .signWith(this.getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String createRefreshToken(Member member) {
        return Jwts.builder()
            .setHeader(this.getHeader())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .claim("username", member.getUsername())
            .signWith(this.getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Map<String, Object> getHeader() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        return headers;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
}
