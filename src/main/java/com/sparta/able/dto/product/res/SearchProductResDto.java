package com.sparta.able.dto.product.res;

import com.sparta.able.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchProductResDto {
    private String name;
    private int price;
    private int count;

    public static SearchProductResDto make(Product product) {
        return SearchProductResDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .count(product.getAmount())
                .build();
    }
}
