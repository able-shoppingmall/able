package com.sparta.able.controller;

import com.sparta.able.dto.ResponseBodyDto;
import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseBodyDto addOrder(@RequestBody @Valid AddOrderReqDto request) {
        return orderService.addOrder(request);
    }
}
