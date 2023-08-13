package me.hounds.wanted.onboarding.domain.content.service;

import me.hounds.wanted.onboarding.domain.board.domain.persist.Board;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.persist.Content;
import me.hounds.wanted.onboarding.global.common.CustomPageResponse;
import me.hounds.wanted.onboarding.support.IntegrationReadTestSupport;
import me.hounds.wanted.onboarding.support.board.GivenBoard;
import me.hounds.wanted.onboarding.support.content.GivenContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ContentReadServiceTest extends IntegrationReadTestSupport {

    Board board;

    @BeforeEach
    void init() {
      board = boardRepository.save(GivenBoard.givenBoardWithCount());

      for (int i = 0; i < 30; i++) {
          Content givenContent = GivenContent.givenContent();

          contentService.create(givenContent, board.getId());
      }
    }

    @Test
    @DisplayName("특정 게시판에 있는 컨텐츠를 페이징하여 조회한다.")
    void findAllByPaging() {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by("title"));

        CustomPageResponse<SimpleContentResponse> withPaging
                = contentReadService.findWithPaging(board.getId(), pageRequest);

        assertThat(withPaging.getData().size()).isEqualTo(20);
        assertThat(withPaging.getPageSize()).isEqualTo(20);
        assertThat(withPaging.getTotalPage()).isEqualTo(2);
        assertThat(withPaging.getTotalElements()).isEqualTo(30);
        assertThat(withPaging.getNumber()).isEqualTo(0);
    }

    @Test
    @DisplayName("게시물의 ID로 해당 게시물을 조회한다.")
    void findById()  {
        SimpleContentResponse response = contentReadService.findById(1L);

        assertThat(response.getTitle()).isEqualTo(GivenContent.GIVEN_TITLE);
        assertThat(response.getDetail()).isEqualTo(GivenContent.GIVEN_DETAIL);
    }
}