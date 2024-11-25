package com.sparta.able.controller;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import com.sparta.able.security.UserDetailsImpl;
import com.sparta.able.service.OrderService;
import com.sparta.able.util.ResponseBodyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sparta.able.enums.ResponseMessage.ORDER_COMPLETED;

@Slf4j(topic = "OrderController")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseBodyDto<AddOrderResDto>> addOrder(@RequestBody @Valid AddOrderReqDto request,
                                                                    @AuthenticationPrincipal UserDetailsImpl loginUser) {
        log.info("OrderController.addOrder() 수행 시작");
        AddOrderResDto data =  orderService.addOrder(request, loginUser.getUser());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBodyDto.success(ORDER_COMPLETED.getMessage(), data));
    }
}
