package me.hounds.wanted.onboarding.domain.content.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class ContentNotFoundException extends CombinedException {
    public ContentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
