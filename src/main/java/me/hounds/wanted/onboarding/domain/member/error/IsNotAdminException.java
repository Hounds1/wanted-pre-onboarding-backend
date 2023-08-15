package me.hounds.wanted.onboarding.domain.member.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class IsNotAdminException extends CombinedException {
    public IsNotAdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}
