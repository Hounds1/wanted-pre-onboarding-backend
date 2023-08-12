package me.hounds.wanted.onboarding.global.exception;

import lombok.Getter;

@Getter
public class CombinedException extends RuntimeException {

    private final ErrorCode errorCode;

    public CombinedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
