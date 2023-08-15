package me.hounds.wanted.onboarding.domain.like.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class LikeHasProblemException extends CombinedException {
    public LikeHasProblemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
