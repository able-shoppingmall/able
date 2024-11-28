package com.sparta.able.service;

import com.sparta.able.dto.product.req.ProductCreateRequestDto;
import com.sparta.able.dto.product.res.ProductListResponseDto;
import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.dto.product.res.SearchProductResDto;
import com.sparta.able.dto.product.res.SearchResultDto;
import com.sparta.able.entity.Product;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import com.sparta.able.redis.LockableService;
import com.sparta.able.redis.RedisLockRepository;
import com.sparta.able.repository.ProductRepository;
import com.sparta.able.security.OwnerDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements LockableService<Product> {

    private final RedisLockRepository redisLockRepository;
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

    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_PRODUCT)
        );

        return product.toResponseDto();
    }

    public ProductListResponseDto getProducts(Pageable pageable) {
        Slice<Product> slice = productRepository.findAll(pageable);

        if (slice.isEmpty() && pageable.getPageNumber() > 0) {
            throw new ApplicationException(ErrorCode.NOT_FOUND);
        }

        return new ProductListResponseDto(slice);
    }

    public void decrease(Long id, int purchasedAmount) throws InterruptedException {
        // Lock 획득
        while (!redisLockRepository.lock(this.getClass().getSimpleName(), id)) {
            Thread.sleep(100);
        }

        // Lock 획득에 성공했다면 로직 실행
        try {
            Product product = productRepository.findById(id).orElseThrow(
                    () -> new ApplicationException(ErrorCode.NOT_FOUND_PRODUCT)
            );

            product.decrease(purchasedAmount);
            productRepository.save(product);
        } finally {
            // 로직이 모두 수행되었다면 Lock 해제
            redisLockRepository.unlock(this.getClass().getSimpleName(), id);
        }
    }

    public ProductResponseDto purchase(Long productId, int amount) throws InterruptedException {
        // Lock 획득
        while (!redisLockRepository.lock(this.getClass().getSimpleName(), productId)) {
            Thread.sleep(100);
        }

        // Lock 획득에 성공했다면 로직 실행
        try {
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new ApplicationException(ErrorCode.NOT_FOUND_PRODUCT)
            );

            product.decrease(amount);
            productRepository.save(product);

            return product.toResponseDto();
        } finally {
            // 로직이 모두 수행되었다면 Lock 해제
            redisLockRepository.unlock(this.getClass().getSimpleName(), productId);
        }
    }
}
