package com.sparta.able.dto.product.req;

import com.sparta.able.entity.Owner;
import com.sparta.able.entity.Product;
import com.sparta.able.enums.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreateRequestDto {
    @NotEmpty(message = "상품 이름은 필수 입력 항목입니다.")
    @Size(min = 1, max = 20, message = "상품 이름은 1글자 이상, 20글자 이하여야 합니다.")
    private String name;

    @NotEmpty(message = "가격은 필수 입력 항목입니다.")
    private int price;

    @NotEmpty(message = "수량은 필수 입력 항목입니다.")
    private int amount;

    @NotEmpty(message = "카테고리는 필수 입력 항목입니다.")
    private String category;

    public Product toEntity(Owner owner){
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .category(Category.parseCategory(category))
                .owner(owner)
                .build();
    }
}
