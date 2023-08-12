package me.hounds.wanted.onboarding.domain.member.exception;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class MemberNotFoundException extends CombinedException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
