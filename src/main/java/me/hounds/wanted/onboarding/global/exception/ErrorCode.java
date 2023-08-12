package me.hounds.wanted.onboarding.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     * Common
     */
    BAD_REQUEST(400, "C001", "잘못된 요청입니다"),

    /**
     * Member
     */
    DUPLICATE_EMAIL(400, "M001", "이미 사용 중인 이메일입니다."),
    PASSWORD_LENGTH_REQUIRED(400, "M002", "비밀번호는 8자 이상이어야 합니다.");

    private final int code;
    private final String status;
    private final String message;

    ErrorCode(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
