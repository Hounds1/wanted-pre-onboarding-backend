package me.hounds.wanted.onboarding.domain.member.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.persist.MemberRepository;
import me.hounds.wanted.onboarding.domain.member.exception.DuplicateEmailException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
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
        if (isDuplicated(member.getEmail()))
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);

        String encoded = passwordEncoder.encode(member.getPassword());
        member.initPassword(encoded);
        member.forJoin();

        return SimpleMemberResponse.of(memberRepository.save(member));
    }


    /**
     * Utils
     */
    private boolean isDuplicated(final String email) {
        return memberRepository.existsByEmail(email);
    }
}
