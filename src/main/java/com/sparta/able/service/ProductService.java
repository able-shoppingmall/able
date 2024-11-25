package com.sparta.able.service;

import com.sparta.able.dto.product.req.ProductCreateRequestDto;
import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.entity.Product;
import com.sparta.able.repository.ProductRepository;
import com.sparta.able.security.OwnerDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreateRequestDto req, OwnerDetailsImpl authUser) {
        Product savedProduct = productRepository.save(req.toEntity(authUser.getOwner()));
        return savedProduct.toResponseDto();
    }
}
