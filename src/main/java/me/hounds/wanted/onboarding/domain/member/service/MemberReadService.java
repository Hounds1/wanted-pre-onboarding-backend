package me.hounds.wanted.onboarding.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.error.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReadService {

    private final MemberRepository memberRepository;

    public SimpleMemberResponse findById(final Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return SimpleMemberResponse.of(findMember);
    }

    public SimpleMemberResponse myInfo(final Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return SimpleMemberResponse.of(findMember);
    }
}
