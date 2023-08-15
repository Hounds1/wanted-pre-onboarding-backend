package me.hounds.wanted.onboarding.domain.like.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByContentIdAndMemberId(final Long contentId, final Long memberId);

    long countByContentId(final Long contentId);

    @Modifying
    @Query("UPDATE Like l SET l.activated = false, l.deleteTime = NOW() WHERE l.contentId = :contentId")
    void deactivatedLikesByContentId(final Long contentId);
}
