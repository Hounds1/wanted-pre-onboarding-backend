package me.hounds.wanted.onboarding.domain.auth.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class CannotReissueException extends CombinedException {
    public CannotReissueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
