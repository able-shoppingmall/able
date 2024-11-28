package com.sparta.able.entity;

import com.sparta.able.enums.CouponStatus;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "coupons")
@NoArgsConstructor
public class Coupon extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private CouponStatus status;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    public void decrease(int amount) {
        if (count - amount < 0) {
            throw new ApplicationException(ErrorCode.INSUFFICIENT_STOCK);
        }

        count -= amount;
    }
}
