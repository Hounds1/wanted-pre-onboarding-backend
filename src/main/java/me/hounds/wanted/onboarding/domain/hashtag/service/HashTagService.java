package me.hounds.wanted.onboarding.domain.hashtag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTagRepository;
import me.hounds.wanted.onboarding.domain.hashtag.error.CannotUseTargetTagException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public HashTag create(final HashTag hashTag) {
        if (!isPresent(hashTag.getTagName()))
            return createNewHashTag(hashTag);
        else
            return findExistingTag(hashTag);
    }

    /**
     * for create()
     */
    private HashTag createNewHashTag(final HashTag hashTag) {
        return hashTagRepository.save(hashTag);
    }

    private HashTag findExistingTag(final HashTag hashTag) {
        return hashTagRepository.findByTagName(hashTag.getTagName())
                .orElseThrow(() -> new CannotUseTargetTagException(ErrorCode.CAN_NOT_USE_TARGET_TAG));
    }

    /**
     * for create()
     */

    private boolean isPresent(final String tagName) {
        return hashTagRepository.existsByTagName(tagName);
    }
}
