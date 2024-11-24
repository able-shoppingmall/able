package com.sparta.able.dto.order.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddOrderReqDto {
    @NotBlank
    private Long productId;
}
