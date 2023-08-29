package me.hounds.wanted.onboarding.domain.contentwithtag.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist.ContentWithHashTag;
import me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist.ContentWithHashTagRepository;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentWithHashTagService {

    private final ContentWithHashTagRepository contentWithHashTagRepository;

    public void create(final Content content, final HashTag hashTag) {
        ContentWithHashTag contentWithHashTag = ContentWithHashTag.from(content, hashTag);

        contentWithHashTagRepository.save(contentWithHashTag);
    }
}
