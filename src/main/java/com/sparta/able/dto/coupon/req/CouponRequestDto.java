package com.sparta.able.dto.coupon.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequestDto {

    @NotNull
    private String name;
    @NotNull
    private int count;

    public CouponRequestDto(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
