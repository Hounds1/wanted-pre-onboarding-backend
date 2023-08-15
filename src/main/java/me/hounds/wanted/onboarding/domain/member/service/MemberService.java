package me.hounds.wanted.onboarding.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.dto.UpdateMemberRequest;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.domain.member.error.DuplicateEmailException;
import me.hounds.wanted.onboarding.domain.member.error.IsNotAdminException;
import me.hounds.wanted.onboarding.domain.member.error.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public SimpleMemberResponse join(final Member member) {
        isDuplicated(member.getEmail());

        String encoded = passwordEncoder.encode(member.getPassword());
        member.initPassword(encoded);
        member.forJoin();

        Member savedMember = memberRepository.save(member);
        return SimpleMemberResponse.of(savedMember);
    }

    public SimpleMemberResponse update(final UpdateMemberRequest request, final Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (StringUtils.hasText(request.getPassword()))
            request.setPassword(passwordEncoder.encode(request.getPassword()));

        findMember.updateInfo(request);

        return SimpleMemberResponse.of(findMember);
    }

    /**
     * 논리적 삭제
     */
    public void delete(final Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        findMember.deactivated();
    }


    /**
     * Utils
     */
    public SimpleMemberResponse rankUp(final Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        isAdmin(findMember.getEmail());

        findMember.changeRole(RoleType.ADMIN);

        return SimpleMemberResponse.of(findMember);
    }

    private void isDuplicated(final String email) {
        if (memberRepository.existsByEmail(email))
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
    }

    private void isAdmin(final String email) {
        if (!email.equals("admin@admin.admin"))
            throw new IsNotAdminException(ErrorCode.IS_NOT_ADMIN);
    }
}
