package com.sparta.able.service;

import com.sparta.able.dto.product.req.ProductCreateRequestDto;
import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.dto.product.res.SearchProductResDto;
import com.sparta.able.dto.product.res.SearchResultDto;
import com.sparta.able.entity.Product;
import com.sparta.able.repository.ProductRepository;
import com.sparta.able.security.OwnerDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreateRequestDto req, OwnerDetailsImpl authUser) {
        Product savedProduct = productRepository.save(req.toEntity(authUser.getOwner()));
        return savedProduct.toResponseDto();
    }

    public SearchResultDto searchProducts(Pageable pageable, String keyword) {
        Page<Product> searchResult = productRepository.findAllToKeyword(pageable, keyword);

        List<SearchProductResDto> contents = searchResult.getContent().stream().map(SearchProductResDto::make).toList();
        return SearchResultDto.make(contents, searchResult.getPageable());
    }
}
