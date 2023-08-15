package me.hounds.wanted.onboarding.domain.auth.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.auth.domain.dto.LoginRequest;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.error.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.global.jwt.TokenProvider;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;

    public TokenDTO login(final LoginRequest request) {
        Member findMember = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        CustomUserDetails userDetails = CustomUserDetails.of(findMember);

        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authenticate = managerBuilder.getObject().authenticate(token);

        return tokenProvider.generateToken(userDetails.getUsername(), authenticate);
    }
}
