package com.strider.strider_common_lib.response;

import com.strider.strider_common_lib.error.StriderErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Builder
@Getter
@AllArgsConstructor
public class StriderResponse<T>{
    public T data;
    public Meta meta;

    public static <T> StriderResponse<T> success(T data) {
        return StriderResponse.<T>builder()
                .data(data)
                .meta(StriderErrorCodes.OK.toMeta())
                .build();
    }

    public static <T> StriderResponse<T> error(StriderErrorCodes errorCode) {
        return StriderResponse.<T>builder()
                .data(null)
                .meta(errorCode.toMeta())
                .build();
    }

    public static <T> StriderResponse<T> responseBuilder(Object data, Class<T> clazz) {
        if (data instanceof StriderErrorCodes errorCode) {
            return StriderResponse.<T>error(errorCode);
        }

        if (data instanceof HttpStatusCode statusCode) {
            StriderErrorCodes errorCode = StriderErrorCodes.fromHttpStatusCode(statusCode);
            return StriderResponse.<T>error(errorCode);
        }

        T castedData = clazz.cast(data);
        return StriderResponse.<T>success(castedData);
    }
}
