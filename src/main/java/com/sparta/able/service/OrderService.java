package com.sparta.able.service;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Transactional
    public AddOrderResDto addOrder(AddOrderReqDto request) {
        return null;
    }
}
