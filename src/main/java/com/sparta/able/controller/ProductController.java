package com.sparta.able.controller;

import com.sparta.able.entity.Product;
import com.sparta.able.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> searchProducts(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return productService.searchProducts(pageable, keyword);
    }
}
