package com.sparta.able.dto.order.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderResDto {
    private final String STATUS_CODE = "Success";
    private final String MESSAGE = "주문 완료";
    private OrderInfo orderInfo;

    public static class OrderInfo {
        private String productName;
        private int price;
    }
}
