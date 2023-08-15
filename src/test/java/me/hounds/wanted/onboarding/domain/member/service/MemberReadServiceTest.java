package me.hounds.wanted.onboarding.domain.member.service;

import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.error.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.support.IntegrationReadTestSupport;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberReadServiceTest extends IntegrationReadTestSupport {

    Member member;

    @BeforeEach
    void init() {
        member = memberRepository.save(GivenMember.givenMember());
    }

    @Test
    void findById() {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        boolean isTrue = findMember.equals(member);

        assertThat(isTrue).isTrue();
    }
}