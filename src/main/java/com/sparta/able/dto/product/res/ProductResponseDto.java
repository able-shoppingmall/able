package com.sparta.able.dto.product.res;

import com.sparta.able.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private int price;
    private int amount;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
