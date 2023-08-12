package me.hounds.wanted.onboarding.global.jwt.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class TokenNotFoundException extends CombinedException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
