package com.sparta.able.enums;

import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;

public enum Category {
    DEFAULT,
    OUTER,
    TOP,
    BOTTOM,
    SHOES,
    OTHER;

    public static Category parseCategory(String category) {
        try {
            return Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(ErrorCode.NOT_FOUND_CATEGORY);
        }
    }
}
