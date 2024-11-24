package com.sparta.able.entity;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ORDERS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    public static Order create(User user, Long productId) {
        return Order.builder()
                .user(user)
                .productId(productId)
                .build();
    }
}
