package com.sparta.able.dto.order.req;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderReqDto {
    @NotNull
    private Long productId;

    @NotNull
    private Long userId;
}
