package me.hounds.wanted.onboarding.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {

    private int code;
    private String status;
    private String message;

    public ExceptionResponse(final ErrorCode errorCode) {
        code = errorCode.getCode();
        status = errorCode.getStatus();
        message = errorCode.getMessage();
    }

    public static ExceptionResponse of(final ErrorCode errorCode) {
        return new ExceptionResponse(errorCode);
    }

    public static ExceptionResponse adviceResponse(final int code, final String status, final String message) {
        return new ExceptionResponse(code, status, message);
    }
}
