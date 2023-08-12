package me.hounds.wanted.onboarding.domain.auth.controller;

import me.hounds.wanted.onboarding.domain.auth.domain.dto.LoginRequest;
import me.hounds.wanted.onboarding.domain.auth.service.AuthService;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.global.jwt.vo.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private final long refreshTokenExpireMils;


    public AuthController(@Value("${jwt.refreshTokenExpireMils}") long refreshTokenExpireMils,
                          AuthService authService) {
        this.refreshTokenExpireMils = refreshTokenExpireMils;
        this.authService = authService;
    }


    @PostMapping("/public/auth")
    public ResponseEntity<AccessToken> login(@RequestBody @Valid LoginRequest request,
                                             HttpServletResponse response) {
        TokenDTO tokens = authService.login(request);

        String refreshToken = tokens.getRefreshToken().getRefreshToken();
        Cookie cookie = generateCookie("refreshToken",
                refreshToken,
                true,
                false,
                "/",
                (int) refreshTokenExpireMils);

        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tokens.getAccessToken());
    }


    /**
     * 쿠키 생성 시 로컬에서 테스트 할 경우
     * secure => false
     */
    private Cookie generateCookie(String key,
                                  String value,
                                  boolean isHttpOnly,
                                  boolean isSecure,
                                  String path,
                                  int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setSecure(isSecure);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        return cookie;
    }
}
