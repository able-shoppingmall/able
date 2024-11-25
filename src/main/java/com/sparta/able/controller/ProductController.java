package com.sparta.able.controller;

import com.sparta.able.dto.product.res.SearchProductResDto;
import com.sparta.able.service.ProductService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search-v1")
    public ResponseEntity<ResponseBodyDto<Page<SearchProductResDto>>> searchProducts(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                                                     @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                                                                     @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        Page<SearchProductResDto> data = productService.searchProducts(pageable, keyword);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseBodyDto.success("상품 검색 성공", data));
    }
}
