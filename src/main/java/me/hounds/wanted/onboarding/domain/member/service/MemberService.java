package me.hounds.wanted.onboarding.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.domain.member.exception.DuplicateEmailException;
import me.hounds.wanted.onboarding.domain.member.exception.MemberNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.global.security.principal.CustomUserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public SimpleMemberResponse rankUp(final Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        findMember.changeRole(RoleType.ADMIN);

        return SimpleMemberResponse.of(findMember);
    }

    /**
     * Utils
     */
    private void isDuplicated(final String email) {
        if (memberRepository.existsByEmail(email))
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
    }
}
