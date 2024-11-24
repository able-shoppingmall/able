package com.sparta.able.controller;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddOrderResDto addOrder(@RequestBody @Valid AddOrderReqDto request) {
        return null;
    }
}
