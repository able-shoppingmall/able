package com.sparta.able.entity;

import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.enums.Category;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public ProductResponseDto toResponseDto() {
        return new ProductResponseDto(
                id,
                name,
                price,
                amount,
                category,
                getCreatedAt(),
                getModifiedAt()
        );
    }

    public void decrease(int purchasedAmount) {
        if (amount - purchasedAmount < 0) {
            throw new ApplicationException(ErrorCode.INSUFFICIENT_STOCK);
        }

        amount -= purchasedAmount;
    }
}
