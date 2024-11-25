package com.sparta.able.controller;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;

import com.sparta.able.service.CouponService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<ResponseBodyDto<CouponResponseDto>> createCoupon(@RequestBody CouponRequestDto requestDto) {
        return new ResponseEntity<>(
                ResponseBodyDto.success("사장님 쿠폰 생성 완료", couponService.createCoupon(requestDto)),
                HttpStatus.OK);
    }

    @PostMapping("/event/{id}")
    public ResponseEntity<?> issueEventCoupon(@PathVariable Long id) {

        return new ResponseEntity<>(
                ResponseBodyDto.success("선착순 쿠폰 발급 완료", couponService.issueEventCoupon(id)),
                HttpStatus.OK);

    }
}
