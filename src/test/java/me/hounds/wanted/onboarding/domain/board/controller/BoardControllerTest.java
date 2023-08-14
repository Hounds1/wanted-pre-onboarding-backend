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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;

import static me.hounds.wanted.onboarding.support.EndPoints.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
                .andDo(document("board-create",
                        requestFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        )))
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
                .andDo(document("board-create-denied",
                        requestFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자는 게시판 자체를 업데이트 할 수 있다.")
    @WithAdmin
    void update() throws Exception {
        UpdateBoardRequest updateRequest = new UpdateBoardRequest(GivenBoard.GIVEN_UPDATE_NAME);
        String requestJson = objectMapper.writeValueAsString(updateRequest);

        when(boardService.update(any(), any())).thenReturn(SimpleBoardResponse.of(GivenBoard.givenBoard()));

        mockMvc.perform(RestDocumentationRequestBuilders.patch(ADMIN_BOARD_WITH_COUNT.getUrl(), 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(document("board-update",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자가 아니라면 업데이트를 거부한다.")
    @WithUser
    void updateDenied() throws Exception {
        UpdateBoardRequest updateRequest = new UpdateBoardRequest(GivenBoard.GIVEN_UPDATE_NAME);
        String requestJson = objectMapper.writeValueAsString(updateRequest);

        when(boardService.update(any(), any())).thenReturn(SimpleBoardResponse.of(GivenBoard.givenBoard()));

        mockMvc.perform(RestDocumentationRequestBuilders.patch(ADMIN_BOARD_WITH_COUNT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isForbidden())
                .andDo(document("board-update-denied",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("boardName").description("게시판 이름")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자는 게시판을 삭제할 수 있다. 단, 논리적 삭제로 진행된다.")
    @WithAdmin
    void deleteBoard() throws Exception{
        doNothing().when(boardService).delete(any());

        mockMvc.perform(RestDocumentationRequestBuilders.delete(ADMIN_BOARD_WITH_COUNT.getUrl(), 1L))
                .andExpect(status().isNoContent())
                .andDo(document("board-delete",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("관리자가 아니라면 삭제 요청을 거부한다.")
    @WithUser
    void deleteDenied() throws Exception{
        doNothing().when(boardService).delete(any());

        mockMvc.perform(RestDocumentationRequestBuilders.delete(ADMIN_BOARD_WITH_COUNT.getUrl(), 1L))
                .andExpect(status().isForbidden())
                .andDo(document("board-delete-denied",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }
}