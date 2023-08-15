package me.hounds.wanted.onboarding.domain.like.controller;

import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.EndPoints;
import me.hounds.wanted.onboarding.support.annotations.withUser.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LikeControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("게시글에 좋아요가 추가된다.")
    @WithUser
    void Test() throws Exception {
        doNothing().when(likeService).likeAndDislike(any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.post(EndPoints.REQUEST_LIKE.getUrl(), 1L))
                .andExpect(status().isOk())
                .andDo(document("like-and-dislike",
                        pathParameters(
                                parameterWithName("contentId").description("컨텐츠 아이디")
                        )))
                .andDo(print());
    }
}