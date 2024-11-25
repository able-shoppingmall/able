package com.sparta.able.enums;

import lombok.Getter;

@Getter
public enum ResponseMessage {
    ORDER_COMPLETED("주문 완료"),
    ;

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
