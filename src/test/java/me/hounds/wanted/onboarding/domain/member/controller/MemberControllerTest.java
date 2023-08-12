package me.hounds.wanted.onboarding.domain.member.controller;

import me.hounds.wanted.onboarding.domain.member.domain.dto.JoinRequest;
import me.hounds.wanted.onboarding.support.ControllerIntegrationTestSupport;
import me.hounds.wanted.onboarding.support.EndPoints;
import me.hounds.wanted.onboarding.support.member.GivenMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static me.hounds.wanted.onboarding.support.EndPoints.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest extends ControllerIntegrationTestSupport {

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void join() throws Exception {
        JoinRequest joinRequest = new JoinRequest(GivenMember.GIVEN_EMAIL, GivenMember.GIVEN_PASSWORD);
        String requestJSON = objectMapper.writeValueAsString(joinRequest);

        mockMvc.perform(post(PUBLIC_MEMBER.getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(status().isCreated())
                .andDo(print());
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
                .andDo(print());
    }
}