package me.hounds.wanted.onboarding.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.global.jwt.vo.AccessToken;
import me.hounds.wanted.onboarding.global.jwt.vo.RefreshToken;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long accessTokenExpireMils;
    private final long refreshTokenExpireMils;
    private final CustomUserDetailsService detailsService;
    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.accessTokenExpireMils}") long accessTokenExpireMils,
                         @Value("${jwt.refreshTokenExpireMils}")long refreshTokenExpireMils,
                         CustomUserDetailsService detailsService) {
        this.secret = secret;
        this.accessTokenExpireMils = accessTokenExpireMils * 1000;
        this.refreshTokenExpireMils = refreshTokenExpireMils * 1000;
        this.detailsService = detailsService;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO generateToken(String email, Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .claim("email", email)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + accessTokenExpireMils))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("email", email)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + refreshTokenExpireMils))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDTO.of(AccessToken.from(accessToken), RefreshToken.from(refreshToken));
    }

    public Authentication getAuthentication(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends SimpleGrantedAuthority> authorities =
                Arrays.stream(claim.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String email = (String) claim.get("email");

        UserDetails principal = detailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key.getEncoded())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | ExpiredJwtException |
                 UnsupportedJwtException e) {
            log.error("[TokenProvider] :: " + e.getMessage());
        }

        return false;
    }
}
