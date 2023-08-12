package me.hounds.wanted.onboarding.domain.member.exception;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class DuplicateEmailException extends CombinedException {

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
