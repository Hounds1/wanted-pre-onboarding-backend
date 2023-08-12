package me.hounds.wanted.onboarding.domain.member.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(final String email);

    Optional<Member> findByEmail(final String email);
}
