package me.hounds.wanted.onboarding.global.exception.advice;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;
import me.hounds.wanted.onboarding.global.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handlingApiException(CombinedException e) {
        ErrorCode errorCode = e.getErrorCode();
        ExceptionResponse response = ExceptionResponse.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errorMessage = fieldErrors.get(0).getDefaultMessage(); // 첫 번째 에러 메시지만 사용

        String errorCode = "INVALID_INPUT_VALUE";

        ExceptionResponse response = ExceptionResponse.adviceResponse(400, errorCode, errorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
