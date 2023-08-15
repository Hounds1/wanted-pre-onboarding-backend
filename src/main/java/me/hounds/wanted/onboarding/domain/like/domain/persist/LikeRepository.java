package me.hounds.wanted.onboarding.domain.like.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByContentIdAndMemberId(final Long contentId, final Long memberId);
}
