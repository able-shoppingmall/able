package com.sparta.able.service;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import com.sparta.able.entity.Order;
import com.sparta.able.entity.Product;
import com.sparta.able.entity.User;
import com.sparta.able.repository.OrderRepository;
import com.sparta.able.repository.ProductRepository;
import com.sparta.able.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public AddOrderResDto addOrder(AddOrderReqDto request) {
        /*
        현재 인증/인가 에 대한 부분이 구현되지 않았으므로 임의적으로 RequestBody 에 요청 사용자 ID 를 전달 중
        인증/인가 를 반영하면 해당 부분 수정 필요
        */
        User customer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Product purchaseProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

        Order newOrder = Order.create(customer, purchaseProduct.getId());
        orderRepository.save(newOrder);

        return AddOrderResDto.make(purchaseProduct.getName(), purchaseProduct.getPrice());
    }
}
