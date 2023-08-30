package me.hounds.wanted.onboarding.domain.auth.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.auth.domain.dto.LoginRequest;
import me.hounds.wanted.onboarding.domain.auth.error.CannotReissueException;
import me.hounds.wanted.onboarding.domain.member.service.MemberReadService;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.global.jwt.TokenProvider;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.global.jwt.vo.AccessToken;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberReadService memberReadService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;

    public TokenDTO login(final LoginRequest request) {
        CustomUserDetails userDetails = memberReadService.findDetailsByEmail(request.getEmail());

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authenticate = managerBuilder.getObject().authenticate(token);

        return tokenProvider.generateToken(userDetails.getUsername(), authenticate);
    }

    public AccessToken reissue(final AccessToken accessToken, final String refreshToken) {
        if (!StringUtils.hasText(refreshToken) || !tokenProvider.validateToken(refreshToken))
            throw new CannotReissueException(ErrorCode.CAN_NOT_REISSUE);

        Authentication authentication = tokenProvider.getAuthentication(accessToken.getAccessToken());
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        return AccessToken.from(tokenProvider.generateToken(principal.getUsername(), authentication)
                .getAccessToken().getAccessToken());
    }
}
