package me.hounds.wanted.onboarding.domain.board.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class DuplicateBoardNameException extends CombinedException {
    public DuplicateBoardNameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
