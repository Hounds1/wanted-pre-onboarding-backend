package me.hounds.wanted.onboarding.domain.auth.controller;

import me.hounds.wanted.onboarding.domain.auth.domain.dto.LoginRequest;
import me.hounds.wanted.onboarding.global.jwt.dto.TokenDTO;
import me.hounds.wanted.onboarding.global.jwt.vo.AccessToken;
import me.hounds.wanted.onboarding.global.jwt.vo.RefreshToken;
import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.EndPoints;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("아이디와 비밀번호로 토큰을 발급받는다.")
    void login() throws Exception {
        LoginRequest request = new LoginRequest(GivenMember.GIVEN_EMAIL, GivenMember.GIVEN_PASSWORD);
        String requestJson = objectMapper.writeValueAsString(request);

        AccessToken accessToken = AccessToken.from("accessToken");
        RefreshToken refreshToken = RefreshToken.from("refreshToken");

        when(authService.login(any())).thenReturn(TokenDTO.of(accessToken, refreshToken));

        mockMvc.perform(post(EndPoints.PUBLIC_AUTH.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andDo(document("auth-login",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("엑세스 토큰")
                        )))
                .andDo(print());
    }
}