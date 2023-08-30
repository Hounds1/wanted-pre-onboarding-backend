package me.hounds.wanted.onboarding.domain.contentwithtag.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist.ContentWithHashTag;
import me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist.ContentWithHashTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentWithHashTagReadService {

    private final ContentWithHashTagRepository contentWithHashTagRepository;

    public List<ContentWithHashTag> findAllByContent(final Content content) {
        return contentWithHashTagRepository.findAllByContent(content);
    }
}
