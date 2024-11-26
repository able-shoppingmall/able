package com.sparta.able.repository;

import com.sparta.able.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByName(String testCoupon);
    Optional<Coupon> findFirstByName(String name);  // 첫 번째 쿠폰만 반환
}
