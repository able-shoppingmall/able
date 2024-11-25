package com.sparta.able.service;

import com.sparta.able.entity.Product;
import com.sparta.able.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 임시로 Page<Product> 타입을 반환하도록 구현, ResponseBodyDto 타입으로 수정해야 함
    // Dto 생성시 페이지 번호 +1 해야 함
    public Page<Product> searchProducts(Pageable pageable, String keyword) {
        return productRepository.findAllToKeyword(pageable, keyword);
    }
}
