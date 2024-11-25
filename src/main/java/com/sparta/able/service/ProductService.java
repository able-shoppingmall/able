package com.sparta.able.service;

import com.sparta.able.dto.product.res.SearchProductResDto;
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

    public Page<SearchProductResDto> searchProducts(Pageable pageable, String keyword) {
        Page<Product> searchResult = productRepository.findAllToKeyword(pageable, keyword);

        return searchResult.map(SearchProductResDto::make);
    }
}
