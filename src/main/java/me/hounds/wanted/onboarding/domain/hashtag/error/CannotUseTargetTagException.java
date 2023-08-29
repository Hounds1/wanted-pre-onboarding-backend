package me.hounds.wanted.onboarding.domain.hashtag.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class CannotUseTargetTagException extends CombinedException {
    public CannotUseTargetTagException(ErrorCode errorCode) {
        super(errorCode);
    }
}
