package com.strider.strider_common_lib.exception;

import com.strider.strider_common_lib.error.StriderErrorCodes;
import com.strider.strider_common_lib.response.StriderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalStriderExceptionHandler {

    public GlobalStriderExceptionHandler(){

    }

    @ExceptionHandler(StriderException.class)
    public ResponseEntity<StriderResponse<Object>> handleStriderException(StriderException ex) {
        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus())
                .body(StriderResponse.error(ex.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StriderResponse<Object>> handleUnknownException(Exception e) {
        return ResponseEntity
                .status(StriderErrorCodes.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(StriderResponse.error(StriderErrorCodes.INTERNAL_SERVER_ERROR));
    }
}
