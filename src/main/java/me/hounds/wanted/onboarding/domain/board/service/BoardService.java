package me.hounds.wanted.onboarding.domain.board.service;

import lombok.RequiredArgsConstructor;
import me.hounds.wanted.onboarding.domain.board.domain.dto.SimpleBoardResponse;
import me.hounds.wanted.onboarding.domain.board.domain.dto.UpdateBoardRequest;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.board.domain.persist.BoardRepository;
import me.hounds.wanted.onboarding.domain.board.error.BoardNotFoundException;
import me.hounds.wanted.onboarding.domain.board.error.DuplicateBoardNameException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public SimpleBoardResponse create(final Board board) {
        isDuplicated(board.getBoardName());

        board.forCreate();
        Board savedBoard = boardRepository.save(board);

        return SimpleBoardResponse.of(savedBoard);
    }

    public SimpleBoardResponse update(final UpdateBoardRequest request, final Long boardId) {
        isDuplicated(request.getBoardName());

        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        findBoard.update(request);

        return SimpleBoardResponse.of(findBoard);
    }

    /**
     * 논리적 삭제
     */
    public void delete(final Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        findBoard.deactivated();
    }

    /**
     * utils
     */
    private void isDuplicated(final String boardName) {
        if (boardRepository.existsByBoardName(boardName))
            throw new DuplicateBoardNameException(ErrorCode.DUPLICATE_BOARD_NAME);
    }
}
