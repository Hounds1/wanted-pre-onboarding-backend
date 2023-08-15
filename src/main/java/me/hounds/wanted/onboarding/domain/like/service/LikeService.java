package me.hounds.wanted.onboarding.domain.like.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.like.domain.persist.Like;
import me.hounds.wanted.onboarding.domain.like.domain.persist.LikeRepository;
import me.hounds.wanted.onboarding.domain.like.error.LikeHasProblemException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;

    public void likeAndDislike(final Long contentId, final Long memberId) {
        try {
            Optional<Like> findLike = likeRepository.findByContentIdAndMemberId(contentId, memberId);

            if (findLike.isPresent()) {
                findLike.get().deactivated();
            } else {
                Like like = Like.of(contentId, memberId);
                like.activated();

                likeRepository.save(like);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LikeHasProblemException(ErrorCode.LIKE_HAS_PROBLEM);
        }
    }

    public void deactivatedWithContent(final Long contentId) {
       likeRepository.deactivatedLikesByContentId(contentId);
    }
}
