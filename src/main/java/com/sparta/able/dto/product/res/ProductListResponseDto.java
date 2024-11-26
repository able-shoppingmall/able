package com.sparta.able.dto.product.res;

import com.sparta.able.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductListResponseDto {
    private long elementsCount;
    private long currentSliceNumber;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    private List<ProductResponseDto> productList;


    public ProductListResponseDto(Slice<Product> slice) {
        this.elementsCount = slice.getNumberOfElements();
        this.currentSliceNumber = slice.getNumber();
        this.hasNext = slice.hasNext();
        this.hasPrevious = slice.hasPrevious();
        this.isFirst = slice.isFirst();
        this.isLast = slice.isLast();
        this.productList = slice.getContent()
                .stream()
                .map(Product::toResponseDto).toList();
    }
}
