package com.strider.strider_common_lib.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
    private String status;
    private Integer code;
    private String message;
}
