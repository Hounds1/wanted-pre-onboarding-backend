package me.hounds.wanted.onboarding.global.jwt.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessToken {

    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String accessToken;

    public static AccessToken from(final String accessToken) {
        return new AccessToken(accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        AccessToken other = (AccessToken) obj;
        return Objects.equals(accessToken, other.accessToken);
    }
}
