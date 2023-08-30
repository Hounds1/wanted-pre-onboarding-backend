package me.hounds.wanted.onboarding.domain.hashtag.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.contentwithtag.domain.persist.ContentWithHashTag;
import me.hounds.wanted.onboarding.domain.contentwithtag.service.ContentWithHashTagReadService;
import me.hounds.wanted.onboarding.domain.hashtag.domain.dto.SimpleHashTagResponse;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagReadService {

    private final ContentWithHashTagReadService contentWithHashTagReadService;
    private final HashTagRepository hashTagRepository;

    public List<SimpleHashTagResponse> findAllByContentId(final Content content) {
        List<ContentWithHashTag> allByContent = contentWithHashTagReadService.findAllByContent(content);

        List<SimpleHashTagResponse> result = new ArrayList<>();
        for (ContentWithHashTag metas : allByContent) {
            Optional<HashTag> findHashTag = hashTagRepository.findById(metas.getHashTag().getId());

            findHashTag.ifPresent(hashTag -> result.add(SimpleHashTagResponse.of(hashTag)));
        }

        return result;
    }
}
