package me.hounds.wanted.onboarding.domain.member.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class DuplicateEmailException extends CombinedException {

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
