package me.hounds.wanted.onboarding.domain.board.service;

import me.hounds.wanted.onboarding.domain.board.domain.dto.SimpleBoardResponse;
import me.hounds.wanted.onboarding.domain.board.domain.dto.UpdateBoardRequest;
import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.support.IntegrationTestSupport;
import me.hounds.wanted.onboarding.support.annotations.withAdmin.WithAdmin;
import me.hounds.wanted.onboarding.support.board.GivenBoard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest extends IntegrationTestSupport {

    Board board;

    @BeforeEach
    void init() {
       board = boardRepository.save(GivenBoard.givenBoardWithCount());
    }

    @Test
    @DisplayName("게시판을 생성 할 수 있다.")
    void create() {
        SimpleBoardResponse response = boardService.create(GivenBoard.givenBoard());

        assertThat(response.getBoardName()).isEqualTo(GivenBoard.GIVEN_BOARD_NAME);
    }

    @Test
    @DisplayName("게시판을 업데이트 할 수 있다.")
    void update() {
        UpdateBoardRequest request = new UpdateBoardRequest(GivenBoard.GIVEN_UPDATE_NAME);

        SimpleBoardResponse response = boardService.update(request, board.getId());

        assertThat(response.getBoardName()).isEqualTo(request.getBoardName());
    }

    @Test
    @DisplayName("게시판을 삭제 할 수 있다.")
    @WithAdmin
    void delete() {
        boardService.delete(board.getId());

        List<Board> all = boardRepository.findAll();

        assertThat(all.isEmpty()).isTrue();
    }
}