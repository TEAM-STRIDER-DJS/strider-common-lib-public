package com.strider.strider_common_lib.error;

import com.strider.strider_common_lib.response.Meta;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum StriderErrorCodes {
    OK(HttpStatus.OK, "success", 2000, "Request Successes"),
    CREATED(HttpStatus.CREATED, "success", 2001, "Created"),
    ACCEPTED(HttpStatus.ACCEPTED, "success", 2002, "Accepted"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "fail", 4000, "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "fail", 4001, "Unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "fail", 4003, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "fail", 4004, "Not found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "fail", 4005, "Method not allowed"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "fail", 5000, "Internal server error");

    public final HttpStatusCode httpStatusCode;
    public final String status;
    public final Integer code;
    public final String message;

    StriderErrorCodes(HttpStatusCode httpStatusCode, String status, Integer code, String message){
        this.status = status;
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }

    public static StriderErrorCodes fromHttpStatusCode(HttpStatusCode statusCode) {
        for (StriderErrorCodes errorCode : StriderErrorCodes.values()) {
            if (errorCode.getHttpStatus().value() == statusCode.value()) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    public static StriderErrorCodes fromStatusCode(int statusCode) {
        for (StriderErrorCodes errorCode : StriderErrorCodes.values()) {
            if (errorCode.getHttpStatus().value() == statusCode) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }

    public Meta toMeta() {
        return Meta.builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }

    public HttpStatusCode getHttpStatus() {
        return httpStatusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
