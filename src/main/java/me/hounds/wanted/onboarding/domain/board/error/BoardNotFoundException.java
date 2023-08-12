package me.hounds.wanted.onboarding.domain.board.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class BoardNotFoundException extends CombinedException {
    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
