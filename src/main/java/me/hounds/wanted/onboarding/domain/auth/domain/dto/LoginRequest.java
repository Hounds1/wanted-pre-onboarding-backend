package me.hounds.wanted.onboarding.domain.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    /**
     * 로그인 과정에서 비밀번호 검증을 통해
     * 비밀번호가 n자리 이상이라는 단서를 남겨
     * 서는 넘겨서는 안됨.
     */
    private String password;
}
