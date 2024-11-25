package com.sparta.able.service;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import com.sparta.able.entity.Order;
import com.sparta.able.entity.Product;
import com.sparta.able.entity.User;
import com.sparta.able.repository.OrderRepository;
import com.sparta.able.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public AddOrderResDto addOrder(AddOrderReqDto request, User loginUser) {
        Product purchaseProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

        Order newOrder = Order.create(loginUser, purchaseProduct.getId());
        orderRepository.save(newOrder);

        return AddOrderResDto.make(purchaseProduct.getName(), purchaseProduct.getPrice());
    }
}
