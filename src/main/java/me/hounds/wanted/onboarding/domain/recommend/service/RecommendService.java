package me.hounds.wanted.onboarding.domain.recommend.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.Recommend;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import me.hounds.wanted.onboarding.domain.recommend.error.LikeHasProblemException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendService {

    private final RecommendRepository recommendRepository;

    public void likeAndDislike(final Long contentId, final Long memberId) {
        try {
            Optional<Recommend> findLike = recommendRepository.findByContentIdAndMemberId(contentId, memberId);

            if (findLike.isPresent()) {
                findLike.get().deactivated();
            } else {
                Recommend recommend = Recommend.of(contentId, memberId);
                recommend.activated();

                recommendRepository.save(recommend);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LikeHasProblemException(ErrorCode.LIKE_HAS_PROBLEM);
        }
    }

    public void deactivatedWithContent(final Long contentId) {
       recommendRepository.deactivatedLikesByContentId(contentId);
    }
}
