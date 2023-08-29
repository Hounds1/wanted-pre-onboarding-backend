package me.hounds.wanted.onboarding.domain.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.error.BoardNotFoundException;
import me.hounds.wanted.onboarding.domain.board.service.BoardReadService;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.error.ContentNotFoundException;
import me.hounds.wanted.onboarding.domain.contentwithtag.service.ContentWithHashTagService;
import me.hounds.wanted.onboarding.domain.hashtag.domain.dto.CreateHashTagRequest;
import me.hounds.wanted.onboarding.domain.hashtag.domain.persist.HashTag;
import me.hounds.wanted.onboarding.domain.hashtag.service.HashTagService;
import me.hounds.wanted.onboarding.domain.recommend.domain.persist.RecommendRepository;
import me.hounds.wanted.onboarding.domain.recommend.service.RecommendReadService;
import me.hounds.wanted.onboarding.domain.recommend.service.RecommendService;
import me.hounds.wanted.onboarding.global.common.error.MetaDataMismatchException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentService {

    private final ContentRepository contentRepository;
    private final BoardReadService boardReadService;
    private final RecommendReadService recommendReadService;
    private final RecommendService recommendService;
    private final HashTagService hashTagService;
    private final ContentWithHashTagService contentWithHashTagService;

    public SimpleContentResponse create(final Content content, final Long boardId, final List<String> hashTags) {
        Board findBoard = findBoardBeforeInit(boardId);

        content.initBoard(findBoard);

        Content savedContent = contentRepository.save(content);

        findBoard.addContent(savedContent);

        if (!hashTags.isEmpty())
            generateHashTag(hashTags, savedContent);

        return SimpleContentResponse.of(savedContent, 0L);
    }

    // TODO: 2023-08-29 update 및 delete 시 Redis에도 함께 반영 될 수 있도록 수정
    public SimpleContentResponse update(final UpdateContentRequest request, final Long contentId, final String email) {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

        isCreatedBy(email, findContent.getCreateBy());

        findContent.update(request);

        long count = recommendReadService.countByContentId(findContent.getId());

        return SimpleContentResponse.of(findContent, count);
    }

    /**
     * 논리적 삭제
     */
    public void delete(final Long contentId, final String email) {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

        isCreatedBy(email, findContent.getCreateBy());

        recommendService.deactivatedWithContent(findContent.getId());
        findContent.deactivated();
    }

    /**
     * for create
     */
    private Board findBoardBeforeInit(final Long boardId) {
        return boardReadService.findByIdForCreateContent(boardId);
    }


    /**
     * for create -> hashTag
     */
    private void generateHashTag(final List<String> hashTags, final Content content) {
        for (String hashTagName : hashTags) {
            HashTag hashTag = CreateHashTagRequest.from(hashTagName).toEntity();

            HashTag savedHashTag = hashTagService.create(hashTag);

            matchingWithHashTag(content, savedHashTag);
        }
    }

    private void matchingWithHashTag(final Content content, final HashTag hashTag) {
        contentWithHashTagService.create(content, hashTag);
    }


    /**
     * common
     */
    private void isCreatedBy(final String email, final String createdBy) {
        log.info("[ContentService :: isCreated] :: Input data is [{}, {}]", email, createdBy);

        if (!email.equals(createdBy) && !email.equals("admin@admin.admin"))
            throw new MetaDataMismatchException(ErrorCode.INFO_MISMATCH);
    }
}
