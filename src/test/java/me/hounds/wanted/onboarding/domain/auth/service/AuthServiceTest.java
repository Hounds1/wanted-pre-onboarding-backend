package me.hounds.wanted.onboarding.domain.auth.service;

import me.hounds.wanted.onboarding.domain.auth.domain.dto.LoginRequest;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest extends IntegrationTestSupport {

    Member member;

    @BeforeEach
    void init() {
        Member givenMember = GivenMember.givenMemberNoCount();
        String encoded = passwordEncoder.encode(GivenMember.GIVEN_PASSWORD);

        givenMember.initPassword(encoded);

        member = memberRepository.save(givenMember);
    }

    @Test
    @DisplayName("아이디와 비밀번호로 토큰을 발급한다.")
    void login() {
        LoginRequest request = new LoginRequest(GivenMember.GIVEN_EMAIL, GivenMember.GIVEN_PASSWORD);

        TokenDTO tokens = authService.login(request);

        assertThat(tokens.getAccessToken().getAccessToken()).isNotBlank();
        assertThat(tokens.getRefreshToken().getRefreshToken()).isNotBlank();
    }
}