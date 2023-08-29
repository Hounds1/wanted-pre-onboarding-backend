package me.hounds.wanted.onboarding.domain.recommend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecommendReadService {

    private final RecommendRepository recommendRepository;

    public long countByContentId(final Long contentId) {
        return recommendRepository.countByContentId(contentId);
    }
}
