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
public class RefreshToken {

    @NotBlank(message = "토큰이 존재하지 않습니다.")
    private String refreshToken;

    public static RefreshToken from(final String refreshToken) {
        return new RefreshToken(refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        RefreshToken other = (RefreshToken) obj;
        return Objects.equals(refreshToken, other.refreshToken);
    }
}
