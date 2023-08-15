package me.hounds.wanted.onboarding.domain.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberRequest {

    @Length(min = 8, message = "비밀번호는 8자 이상입니다.")
    private String password;
}
