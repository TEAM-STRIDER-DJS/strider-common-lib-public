package com.strider.strider_common_lib.exception;

import com.strider.strider_common_lib.error.StriderErrorCodes;
import lombok.Getter;

@Getter
public class StriderException extends RuntimeException {
    private final StriderErrorCodes errorCode;

    public StriderException(StriderErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public StriderException(StriderErrorCodes errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}
