package com.sparta.able.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseBodyDto {
    private String statusCode;
    private String message;
    private Object data;

    public static ResponseBodyDto make(String statusCode, String message, Object data) {
        return ResponseBodyDto.builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }
}
