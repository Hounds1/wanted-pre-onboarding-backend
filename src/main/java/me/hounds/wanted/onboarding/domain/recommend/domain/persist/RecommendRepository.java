package me.hounds.wanted.onboarding.domain.recommend.domain.persist;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend,Long> {

    Optional<Recommend> findByContentIdAndMemberId(final Long contentId, final Long memberId);

    long countByContentId(final Long contentId);

    @Modifying
    @Query("UPDATE Recommend r SET r.activated = false, r.deleteTime = NOW() WHERE r.contentId = :contentId")
    void deactivatedLikesByContentId(final Long contentId);

    @Query("SELECT r.contentId, COUNT(r) AS likeCount " +
            "FROM Recommend r " +
            "WHERE r.activated = true " +
            "GROUP BY r.contentId " +
            "ORDER BY likeCount DESC ")
    List<Object[]> findContentWithMostLikes(final Pageable pageable);
}
