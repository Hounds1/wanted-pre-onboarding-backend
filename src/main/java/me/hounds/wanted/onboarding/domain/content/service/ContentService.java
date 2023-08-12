package me.hounds.wanted.onboarding.domain.content.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.error.BoardNotFoundException;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.domain.content.domain.persist.ContentRepository;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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
}
