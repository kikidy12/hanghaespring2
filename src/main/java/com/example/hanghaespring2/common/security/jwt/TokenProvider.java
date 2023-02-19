package com.example.hanghaespring2.common.security.jwt;


import com.example.hanghaespring2.common.entity.UserRoleEnum;
import com.example.hanghaespring2.common.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String ACCESS_TOKEN = "access-token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 24 * 60 * 60 * 1000L;

    private final CustomUserDetailService userDetailsService;

    private final Key key;

    public TokenProvider(@Value("${jwt.secret.key}") String secretKey, CustomUserDetailService detailService) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        userDetailsService = detailService;
    }

    public String createToken(String username, UserRoleEnum role) {
        Instant now = Instant.now();
        Instant expiry = now.plus(5, ChronoUnit.HOURS);

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String username, UserRoleEnum role) {
        Instant now = Instant.now();
        Instant expiry = now.plus(7, ChronoUnit.DAYS);

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims.get(AUTHORIZATION_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORIZATION_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());


        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Instant now = Instant.now();
        Instant expiry = now.plus(7, ChronoUnit.DAYS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenAboutToExpire(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Instant expiry = claims.getExpiration().toInstant();
        Instant now = Instant.now();
        Instant threshold = now.plus(5, ChronoUnit.MINUTES);

        return expiry.isBefore(threshold);
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}