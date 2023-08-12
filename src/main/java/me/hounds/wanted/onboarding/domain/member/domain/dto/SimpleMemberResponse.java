package me.hounds.wanted.onboarding.domain.member.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleMemberResponse {

    private String email;
    private RoleType role;

    public static SimpleMemberResponse of(final Member member) {
        return new SimpleMemberResponse(member.getEmail(), member.getRole());
    }
}
