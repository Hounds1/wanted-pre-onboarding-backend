package me.hounds.wanted.onboarding.domain.board.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.error.BoardNotFoundException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardReadService {

    private final BoardRepository boardRepository;

    public Board findByIdForCreateContent(final Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }
}
