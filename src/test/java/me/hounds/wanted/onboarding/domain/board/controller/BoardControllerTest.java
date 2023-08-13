package me.hounds.wanted.onboarding.domain.board.controller;

import me.hounds.wanted.onboarding.domain.board.domain.dto.CreateBoardRequest;
import me.hounds.wanted.onboarding.domain.board.domain.dto.SimpleBoardResponse;
import me.hounds.wanted.onboarding.domain.board.domain.dto.UpdateBoardRequest;
import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.annotations.withAdmin.WithAdmin;
import me.hounds.wanted.onboarding.support.annotations.withUser.WithUser;
import me.hounds.wanted.onboarding.support.board.GivenBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static me.hounds.wanted.onboarding.support.EndPoints.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시판 테스트")
class BoardControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("운영자는 게시판을 생성할 수 있다.")
    @WithAdmin
    void create() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest(GivenBoard.GIVEN_BOARD_NAME);
        String requestJson = objectMapper.writeValueAsString(request);

        when(boardService.create(any())).thenReturn(SimpleBoardResponse.of(request.toEntity()));

        mockMvc.perform(post(ADMIN_BOARD.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("운영자가 아닌 경우 생성 요청을 거부한다.")
    @WithUser
    void createDenied() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest(GivenBoard.GIVEN_BOARD_NAME);
        String requestJson = objectMapper.writeValueAsString(request);

        when(boardService.create(any())).thenReturn(SimpleBoardResponse.of(request.toEntity()));

        mockMvc.perform(post(ADMIN_BOARD.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자는 게시판 자체를 업데이트 할 수 있다.")
    @WithAdmin
    void update() throws Exception {
        UpdateBoardRequest updateRequest = new UpdateBoardRequest(GivenBoard.GIVEN_UPDATE_NAME);
        String requestJson = objectMapper.writeValueAsString(updateRequest);

        when(boardService.update(any(), any())).thenReturn(SimpleBoardResponse.of(GivenBoard.givenBoard()));

        mockMvc.perform(patch(ADMIN_BOARD_WITH_COUNT.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자가 아니라면 업데이트를 거부한다.")
    @WithUser
    void updateDenied() throws Exception {
        UpdateBoardRequest updateRequest = new UpdateBoardRequest(GivenBoard.GIVEN_UPDATE_NAME);
        String requestJson = objectMapper.writeValueAsString(updateRequest);

        when(boardService.update(any(), any())).thenReturn(SimpleBoardResponse.of(GivenBoard.givenBoard()));

        mockMvc.perform(patch(ADMIN_BOARD_WITH_COUNT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자는 게시판을 삭제할 수 있다. 단, 논리적 삭제로 진행된다.")
    @WithAdmin
    void deleteBoard() throws Exception{
        doNothing().when(boardService).delete(any());

        mockMvc.perform(delete(ADMIN_BOARD_WITH_COUNT.getUrl()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자가 아니라면 삭제 요청을 거부한다.")
    @WithUser
    void deleteDenied() throws Exception{
        doNothing().when(boardService).delete(any());

        mockMvc.perform(delete(ADMIN_BOARD_WITH_COUNT.getUrl()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}