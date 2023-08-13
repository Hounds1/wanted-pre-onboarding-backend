package me.hounds.wanted.onboarding.global.common.error;

import me.hounds.wanted.onboarding.global.exception.CombinedException;
import me.hounds.wanted.onboarding.global.exception.ErrorCode;

public class MetaDataMismatchException extends CombinedException {

    public MetaDataMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
