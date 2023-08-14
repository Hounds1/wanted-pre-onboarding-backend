package me.hounds.wanted.onboarding.domain.member.controller;

import me.hounds.wanted.onboarding.domain.member.domain.dto.JoinRequest;
import me.hounds.wanted.onboarding.domain.member.domain.dto.SimpleMemberResponse;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.domain.member.domain.vo.RoleType;
import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static me.hounds.wanted.onboarding.support.EndPoints.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void join() throws Exception {
        JoinRequest joinRequest = new JoinRequest(GivenMember.GIVEN_EMAIL, GivenMember.GIVEN_PASSWORD);
        String requestJSON = objectMapper.writeValueAsString(joinRequest);

        Member member = joinRequest.toEntity();
        member.changeRole(RoleType.USER);

        when(memberService.join(any())).thenReturn(SimpleMemberResponse.of(member));

        mockMvc.perform(post(PUBLIC_MEMBER.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("member-join",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("role").description("리그 오브 레전드")
                        )));
    }

    @Test
    @DisplayName("이메일 형식이 아닐 경우 예외를 던진다.")
    void emailValidException() throws Exception {
        JoinRequest joinRequest = new JoinRequest("test", GivenMember.GIVEN_PASSWORD);
        String requestJson = objectMapper.writeValueAsString(joinRequest);

        mockMvc.perform(post(PUBLIC_MEMBER.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("member-join-denied-email-form",
                        requestFields(
                        fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태값"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호가 8자리 이하일 경우 예외를 던진다.")
    void passwordValidException() throws Exception {
        JoinRequest joinRequest = new JoinRequest(GivenMember.GIVEN_EMAIL, "test");
        String requestJson = objectMapper.writeValueAsString(joinRequest);

        mockMvc.perform(post(PUBLIC_MEMBER.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(document("member-join-denied-password-length",
                        requestFields(
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").description("코드"),
                                fieldWithPath("status").description("상태"),
                                fieldWithPath("message").description("메시지")
                        )))
                .andDo(print());
    }
}