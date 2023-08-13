package me.hounds.wanted.onboarding.domain.content.controller;

import me.hounds.wanted.onboarding.domain.content.domain.dto.CreateContentRequest;
import me.hounds.wanted.onboarding.domain.content.domain.dto.SimpleContentResponse;
import me.hounds.wanted.onboarding.domain.content.domain.dto.UpdateContentRequest;
import me.hounds.wanted.onboarding.global.common.CustomPageResponse;
import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.EndPoints;
import me.hounds.wanted.onboarding.support.annotations.withUser.WithUser;
import me.hounds.wanted.onboarding.support.content.GivenContent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContentControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("회원만 게시물을 생성할 수 있다.")
    @WithUser
    void create() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest(GivenContent.GIVEN_TITLE, GivenContent.GIVEN_DETAIL);
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(post(EndPoints.USER_CONTENT.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아닐 경우 게시물 생성 요청을 거부한다.")
    void createDenied() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest(GivenContent.GIVEN_TITLE, GivenContent.GIVEN_DETAIL);
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(post(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("생성 시 제목이 없다면 예외를 던진다.")
    @WithUser
    void createDeniedWhenTitleIsBlank() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest("", GivenContent.GIVEN_DETAIL);
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(post(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("생성 시 내용이 없다면 예외를 던진다.")
    @WithUser
    void createDeniedWhenDetailIsBlank() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest(GivenContent.GIVEN_TITLE, "");
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(post(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물을 업데이트 할 수 있다.")
    @WithUser
    void update() throws Exception {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, GivenContent.GIVEN_UPDATE_DETAIL);

        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.update(any(), any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(put(EndPoints.USER_CONTENT.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아니라면 업데이트 요청을 거부한다.")
    void updateDenied() throws Exception {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, GivenContent.GIVEN_UPDATE_DETAIL);

        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.update(any(), any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(put(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("업데이트 시 제목이 없다면 예외를 던진다.")
    @WithUser
    void updateDeniedWhenTitleIsBlank() throws Exception {
        UpdateContentRequest request
                = new UpdateContentRequest("", GivenContent.GIVEN_UPDATE_DETAIL);

        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.update(any(), any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(put(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("업데이트 시 내용이 없다면 예외를 던진다.")
    @WithUser
    void updateDeniedWhenDetailIsBlank() throws Exception {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, "");

        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.update(any(), any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(put(EndPoints.USER_CONTENT.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("게시물을 삭제할 수 있다.")
    @WithUser
    void deleteContent() throws Exception {
        doNothing().when(contentService).delete(any(), any());

        mockMvc.perform(delete(EndPoints.USER_CONTENT.getUrl()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아니라면 삭제 요청을 거부한다.")
    void deleteDenied() throws Exception {
        doNothing().when(contentService).delete(any(), any());

        mockMvc.perform(delete(EndPoints.USER_CONTENT.getUrl()))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("작성된 컨텐츠를 페이징 처리하여 불러온다.")
    void findContentsByPaging() throws Exception {
        CustomPageResponse<SimpleContentResponse> paging
                = CustomPageResponse.of(Page.empty());

        when(contentReadService.findWithPaging(any(), any())).thenReturn(paging);

        mockMvc.perform(get(EndPoints.PUBLIC_CONTENT_PAGING.getUrl()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("작성된 컨텐츠를 ID로 선택하여 불러온다.")
    void findById() throws Exception {
        SimpleContentResponse response
                = SimpleContentResponse.of(GivenContent.givenContentWithCount());

        when(contentReadService.findById(any())).thenReturn(response);

        mockMvc.perform(get(EndPoints.PUBLIC_CONTENT_ID.getUrl()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}