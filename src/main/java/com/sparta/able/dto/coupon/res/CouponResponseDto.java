package com.sparta.able.dto.coupon.res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CouponResponseDto {
    private String name;
    private int count;
    private String discount;
}
