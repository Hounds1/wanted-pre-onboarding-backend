package me.hounds.wanted.onboarding.domain.content.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.error.BoardNotFoundException;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.domain.content.error.ContentNotFoundException;
import me.hounds.wanted.onboarding.global.common.error.MetaDataMismatchException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContentService {

    private final BoardRepository boardRepository;
    private final ContentRepository contentRepository;

    public SimpleContentResponse create(final Content content, final Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        content.initBoard(findBoard);
        Content savedContent = contentRepository.save(content);

        findBoard.addContent(savedContent);

        return SimpleContentResponse.of(savedContent);
    }

    public SimpleContentResponse update(final UpdateContentRequest request, final Long contentId, final String email) {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

        isCreatedBy(email, findContent.getCreateBy());

        findContent.update(request);

        return SimpleContentResponse.of(findContent);
    }

    /**
     * 논리적 삭제
     */
    public void delete(final Long contentId, final String email) {
        Content findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(ErrorCode.CONTENT_NOT_FOUND));

        isCreatedBy(email, findContent.getCreateBy());

        findContent.deactivated();
    }


    private void isCreatedBy(final String email, final String createdBy) {
        log.info("Input data is [{}, {}]", email, createdBy);

        if (!email.equals(createdBy))
            throw new MetaDataMismatchException(ErrorCode.INFO_MISMATCH);
        else if (email.equals("admin@admin.admin"))
            return;
    }
}
