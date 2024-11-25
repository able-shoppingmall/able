package com.sparta.able.controller;

import com.sparta.able.dto.product.req.ProductCreateRequestDto;
import com.sparta.able.dto.product.res.ProductResponseDto;
import com.sparta.able.security.OwnerDetailsImpl;
import com.sparta.able.service.ProductService;
import com.sparta.able.util.ResponseBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Secured("ROLE_OWNER")
    @PostMapping
    public ResponseEntity<ResponseBodyDto<ProductResponseDto>> createMenu(@RequestBody ProductCreateRequestDto req, @PathVariable Long storeId, OwnerDetailsImpl authUser) {
        return new ResponseEntity<>(
                ResponseBodyDto.success("메뉴 생성 완료",
                        productService.createProduct(req, authUser)),
                HttpStatus.OK);
    }
}
