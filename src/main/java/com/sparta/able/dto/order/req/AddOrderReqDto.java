package com.sparta.able.dto.order.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddOrderReqDto {
    @NotNull
    private Long productId;
}
