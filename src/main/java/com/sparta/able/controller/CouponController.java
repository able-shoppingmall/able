package com.sparta.able.controller;

import com.sparta.able.dto.coupon.req.CouponRequestDto;
import com.sparta.able.dto.coupon.res.CouponResponseDto;

import com.sparta.able.service.CouponService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    //사장님 쿠폰 생성
    @PostMapping
    public ResponseEntity<ResponseBodyDto<CouponResponseDto>> createCoupon(@RequestBody CouponRequestDto requestDto) {
        return new ResponseEntity<>(
                ResponseBodyDto.success("사장님 쿠폰 생성 완료", couponService.createCoupon(requestDto)),
                HttpStatus.OK);
    }

    //선착순 쿠폰 발급
    @PostMapping("/event/{id}")
    public ResponseEntity<ResponseBodyDto<String>> issueEventCoupon(@PathVariable Long id) {

        return new ResponseEntity<>(
                ResponseBodyDto.success("선착순 쿠폰 발급 완료"),
                HttpStatus.OK);

    }

    // 발급받은 쿠폰 조회
    @GetMapping
    public ResponseEntity<ResponseBodyDto<List<CouponResponseDto>>> getAllCoupons() {
        List<CouponResponseDto> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(
                ResponseBodyDto.success("쿠폰 조회 성공", coupons),
                HttpStatus.OK
        );
    }

}