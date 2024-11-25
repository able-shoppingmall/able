package com.sparta.able.repository;

import com.sparta.able.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryQueryDsl {
    Page<Product> findAllToKeyword(Pageable pageable, String keyword);
}
