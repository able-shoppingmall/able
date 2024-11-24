package com.sparta.able.enums;

import lombok.Getter;

@Getter
public enum ResponseStatusCode {
    SUCCESS("Success"),
    FAIL("Fail"),
    ;

    private final String code;

    ResponseStatusCode(String code) {
        this.code = code;
    }
}
