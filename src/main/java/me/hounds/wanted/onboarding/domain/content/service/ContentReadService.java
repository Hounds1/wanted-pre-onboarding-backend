package me.hounds.wanted.onboarding.domain.content.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.dto.SimpleBoardResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.error.ContentNotFoundException;
import me.hounds.wanted.onboarding.domain.like.domain.persist.LikeRepository;
import me.hounds.wanted.onboarding.global.common.CustomPageResponse;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentReadService {

    private final ContentRepository contentRepository;
    private final LikeRepository likeRepository;
    private final ObjectMapper mapper;

    public CustomPageResponse<SimpleContentResponse> findWithPaging(final Long boardId, final Pageable pageable) {
        Page<Content> findContents = contentRepository.findAllByBoardId(pageable, boardId);

//        Page<SimpleContentResponse> mappedResponse
//                = findContents.map(content -> mapper.convertValue(content, SimpleContentResponse.class));

        Page<SimpleContentResponse> mappedResponse
                = findContents.map(content -> {
            long count = likeRepository.countByContentId(content.getId());
            return SimpleContentResponse.of(content, count);
        });


        return CustomPageResponse.of(mappedResponse);
    }

    public SimpleContentResponse findById(final Long contentId) {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

        long count = likeRepository.countByContentId(contentId);

        return SimpleContentResponse.of(findContent, count);
    }
}
