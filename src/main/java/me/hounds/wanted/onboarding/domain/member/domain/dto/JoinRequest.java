package me.hounds.wanted.onboarding.domain.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hounds.wanted.onboarding.domain.member.domain.persist.Member;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Length(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .build();
    }
}
