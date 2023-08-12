package me.hounds.wanted.onboarding.domain.member.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(final String email);
}
