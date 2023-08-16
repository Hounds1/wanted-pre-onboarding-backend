package me.hounds.wanted.onboarding.domain.recommend.controller;

import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.annotations.withUser.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import static me.hounds.wanted.onboarding.support.EndPoints.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecommendControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("게시글에 좋아요가 추가된다.")
    @WithUser
    void Test() throws Exception {
        doNothing().when(recommendService).likeAndDislike(any(), any());

        mockMvc.perform(RestDocumentationRequestBuilders.post(REQUEST_LIKE.getUrl(), 1L))
                .andExpect(status().isOk())
                .andDo(document("recommend-and-dislike",
                        pathParameters(
                                parameterWithName("contentId").description("컨텐츠 아이디")
                        )))
                .andDo(print());
    }
}