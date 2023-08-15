package me.hounds.wanted.onboarding.support.member;

import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;

public class GivenMember {

    public static final String GIVEN_EMAIL = "test@test.test";
    public static final String GIVEN_PASSWORD = "testTest";

    public static final String GIVEN_ADMIN = "admin@admin.admin";
    public static final String GIVEN_ADMIN_PASSWORD = "admin1234";

    public static Member givenMember() {
        return Member.builder()
                .id(1L)
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .role(RoleType.USER)
                .activated(true)
                .build();
    }

    public static Member givenMemberNoCount() {
        return Member.builder()
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .role(RoleType.USER)
                .activated(true)
                .build();
    }

    public static Member givenAdminMember() {
        return Member.builder()
                .id(1L)
                .email(GIVEN_ADMIN)
                .password(GIVEN_ADMIN_PASSWORD)
                .role(RoleType.ADMIN)
                .activated(true)
                .build();
    }
}
