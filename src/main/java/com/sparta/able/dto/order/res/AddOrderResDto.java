package com.sparta.able.dto.order.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderResDto {
    private String productName;
    private int price;

    public static AddOrderResDto make(String productName, int price) {
        return AddOrderResDto.builder()
                .productName(productName)
                .price(price)
                .build();
    }
}
