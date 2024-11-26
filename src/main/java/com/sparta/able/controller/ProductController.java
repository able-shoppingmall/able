package com.sparta.able.controller;

import com.sparta.able.dto.product.req.ProductCreateRequestDto;
import com.sparta.able.dto.product.res.ProductListResponseDto;
import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.security.OwnerDetailsImpl;
import com.sparta.able.service.ProductService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.sparta.able.dto.product.res.SearchResultDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Secured("ROLE_OWNER")
    @PostMapping
    public ResponseEntity<ResponseBodyDto<ProductResponseDto>> createProduct(@RequestBody ProductCreateRequestDto req, @AuthenticationPrincipal OwnerDetailsImpl authUser) {
        return new ResponseEntity<>(
                ResponseBodyDto.success("상품 생성 완료",
                        productService.createProduct(req, authUser)),
                HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseBodyDto<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(
                ResponseBodyDto.success(
                        "상품 단일 조회 성공",
                        productService.getProduct(productId)
                ),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseBodyDto<ProductListResponseDto>> getProducts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(
                ResponseBodyDto.success(
                        "상품 전체 조회 성공",
                        productService.getProducts(pageable)
                ),
                HttpStatus.OK);
    }


    @GetMapping("/search-v1")
    public ResponseEntity<ResponseBodyDto<SearchResultDto>> searchProducts(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                                           @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                                                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        SearchResultDto data = productService.searchProducts(pageable, keyword);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBodyDto.success("상품 검색 성공", data));
    }
}
