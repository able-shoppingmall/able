package com.sparta.able.controller;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;

import com.sparta.able.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponseDto> createCoupon(@RequestBody CouponRequestDto requestDto) {
        CouponResponseDto responseDto = couponService.createCoupon(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/event/{id}")
    public ResponseEntity<?> issueEventCoupon(@PathVariable Long id) {
        String responseMessage = couponService.issueEventCoupon(id);
        return ResponseEntity.ok().body(responseMessage);
    }
}
