package me.hounds.wanted.onboarding.domain.content.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class CannotReadContentsException extends CombinedException {
    public CannotReadContentsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
