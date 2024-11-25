package com.sparta.able.service;

import com.sparta.able.dto.order.req.AddOrderReqDto;
import com.sparta.able.dto.order.res.AddOrderResDto;
import com.sparta.able.entity.Order;
import com.sparta.able.entity.Product;
import com.sparta.able.entity.User;
import com.sparta.able.exception.InvalidRequestException;
import com.sparta.able.repository.OrderRepository;
import com.sparta.able.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.able.exception.ErrorCode.NOT_FOUND_PRODUCT;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public AddOrderResDto addOrder(AddOrderReqDto request, User loginUser) {
        Product purchaseProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new InvalidRequestException(NOT_FOUND_PRODUCT.getMessage()));

        Order newOrder = Order.create(loginUser, purchaseProduct.getId());
        orderRepository.save(newOrder);

        return AddOrderResDto.make(purchaseProduct.getName(), purchaseProduct.getPrice());
    }
}
