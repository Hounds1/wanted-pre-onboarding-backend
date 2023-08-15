package me.hounds.wanted.onboarding.domain.member.service;

import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.dto.UpdateMemberRequest;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.domain.member.error.DuplicateEmailException;
import me.hounds.wanted.onboarding.domain.member.error.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {

    Member member;

    @BeforeEach
    void init() {
        Member givenMember = GivenMember.givenMember();
        member = memberRepository.save(givenMember);
    }

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void join() {
        Member testMember = Member.builder()
                .email("email@email.com")
                .password("testMember")
                .build();

        SimpleMemberResponse savedMember
                = memberService.join(testMember);

        assertThat(savedMember.getEmail()).isEqualTo(testMember.getEmail());
        assertThat(savedMember.getRole()).isEqualTo(RoleType.USER);
    }

    @Test
    @DisplayName("회원가입 시 이메일이 중복되면 예외를 던진다.")
    void joinThrowsExceptionWhenDuplicateEmail() {
        Member givenMember = GivenMember.givenMemberNoCount();

        assertThrows(DuplicateEmailException.class, () -> memberService.join(givenMember));
    }

    @Test
    @DisplayName("자신의 정보를 업데이트 할 수 있다.")
    void update() {
        final String newPassword = "updateTestPassword";
        UpdateMemberRequest request = new UpdateMemberRequest(newPassword);

        memberService.update(request, member.getId());

        boolean isMatches = passwordEncoder.matches(newPassword, member.getPassword());

        assertThat(isMatches).isTrue();
    }

    @Test
    @DisplayName("계정을 비활성화 할 수 있다.")
    void delete() {
        member.deactivated();

        List<Member> all = memberRepository.findAll();

        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("관리자 이메일을 관리자 계정으로 만든다.")
    void rankUp() {
        Member givenAdmin = Member.builder()
                .email(GivenMember.GIVEN_ADMIN)
                .password(GivenMember.GIVEN_ADMIN_PASSWORD)
                .build();

        Member savedAdmin = memberRepository.save(givenAdmin);

        memberService.rankUp(savedAdmin.getId());

        assertThat(savedAdmin.getRole()).isEqualTo(RoleType.ADMIN);
    }
}