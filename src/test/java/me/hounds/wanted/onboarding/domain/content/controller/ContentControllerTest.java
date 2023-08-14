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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

class ContentControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("회원만 게시물을 생성할 수 있다.")
    @WithUser
    void create() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest(GivenContent.GIVEN_TITLE, GivenContent.GIVEN_DETAIL);
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(RestDocumentationRequestBuilders.post(EndPoints.USER_CONTENT.getUrl(), 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(document("content-create" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용"),
                                fieldWithPath("createdTime").description("생성 시간"),
                                fieldWithPath("createdBy").description("게시자"),
                                fieldWithPath("lastModifiedTime").description("최종 수정 시간"),
                                fieldWithPath("lastModifiedBy").description("최종 수정자")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아닐 경우 게시물 생성 요청을 거부한다.")
    void createDenied() throws Exception {
        CreateContentRequest request
                = new CreateContentRequest(GivenContent.GIVEN_TITLE, GivenContent.GIVEN_DETAIL);
        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.create(any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(RestDocumentationRequestBuilders.post(EndPoints.USER_CONTENT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(document("content-create-denied" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
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

        mockMvc.perform(RestDocumentationRequestBuilders.post(EndPoints.USER_CONTENT.getUrl(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("content-create-denied-title-blank" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
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

        mockMvc.perform(RestDocumentationRequestBuilders.post(EndPoints.USER_CONTENT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("content-create-denied-detail-blank" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
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

        mockMvc.perform(RestDocumentationRequestBuilders.put(EndPoints.USER_CONTENT.getUrl(), 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(document("content-update" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용"),
                                fieldWithPath("createdTime").description("생성 시간"),
                                fieldWithPath("createdBy").description("게시자"),
                                fieldWithPath("lastModifiedTime").description("최종 수정 시간"),
                                fieldWithPath("lastModifiedBy").description("최종 수정자")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아니라면 업데이트 요청을 거부한다.")
    void updateDenied() throws Exception {
        UpdateContentRequest request
                = new UpdateContentRequest(GivenContent.GIVEN_UPDATE, GivenContent.GIVEN_UPDATE_DETAIL);

        String requestJson = objectMapper.writeValueAsString(request);

        when(contentService.update(any(), any(), any())).thenReturn(SimpleContentResponse.of(GivenContent.givenContentWithCount()));

        mockMvc.perform(RestDocumentationRequestBuilders.put(EndPoints.USER_CONTENT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isUnauthorized())
                .andDo(document("content-update-denied" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
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

        mockMvc.perform(RestDocumentationRequestBuilders.put(EndPoints.USER_CONTENT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("content-update-denied-title-blank" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
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

        mockMvc.perform(RestDocumentationRequestBuilders.put(EndPoints.USER_CONTENT.getUrl(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("content-update-denied-detail-blank" ,
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("게시물을 삭제할 수 있다.")
    @WithUser
    void deleteContent() throws Exception {
        doNothing().when(contentService).delete(any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.delete(EndPoints.USER_CONTENT_DELETE.getUrl(), 1L))
                .andExpect(status().isNoContent())
                .andDo(document("content-delete",
                        pathParameters(
                                parameterWithName("contentId").description("게시물 ID")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원이 아니라면 삭제 요청을 거부한다.")
    void deleteDenied() throws Exception {
        doNothing().when(contentService).delete(any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.delete(EndPoints.USER_CONTENT_DELETE.getUrl(), 1L))
                .andExpect(status().isUnauthorized())
                .andDo(document("content-delete-denied",
                        pathParameters(
                                parameterWithName("contentId").description("게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("작성된 컨텐츠를 페이징 처리하여 불러온다.")
    void findContentsByPaging() throws Exception {
        CustomPageResponse<SimpleContentResponse> paging
                = CustomPageResponse.of(Page.empty());

        when(contentReadService.findWithPaging(any(), any())).thenReturn(paging);

        mockMvc.perform(RestDocumentationRequestBuilders.get(EndPoints.PUBLIC_CONTENT_PAGING.getUrl(), 1L)
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andDo(document("content-search-paging",
                        pathParameters(
                                parameterWithName("boardId").description("게시판 ID")
                        ),
                        requestParameters(
                                parameterWithName("page").description("요청 페이지")
                        ),
                        responseFields(
                                fieldWithPath("data").description("결과"),
                                fieldWithPath("totalPage").description("전체 페이지 수"),
                                fieldWithPath("pageSize").description("페이지당 컨텐츠 수"),
                                fieldWithPath("totalElements").description("총 컨텐츠 수"),
                                fieldWithPath("number").description("현재 페이지")
                        )
                ))
                .andDo(print());
    }

    @Test
    @DisplayName("작성된 컨텐츠를 ID로 선택하여 불러온다.")
    void findById() throws Exception {
        SimpleContentResponse response
                = SimpleContentResponse.of(GivenContent.givenContentWithCount());

        when(contentReadService.findById(any())).thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.get(EndPoints.PUBLIC_CONTENT_ID.getUrl())
                        .param("contentId", "1"))
                .andExpect(status().isOk())
                .andDo(document("content-search-id",
                        requestParameters(
                                parameterWithName("contentId").description("컨텐츠 ID")
                        ),
                        responseFields(
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("detail").description("내용"),
                                fieldWithPath("createdTime").description("생성 시간"),
                                fieldWithPath("createdBy").description("게시자"),
                                fieldWithPath("lastModifiedTime").description("최종 수정 시간"),
                                fieldWithPath("lastModifiedBy").description("최종 수정자")
                        )))
                .andDo(print());
    }
}