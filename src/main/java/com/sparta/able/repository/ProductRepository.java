package com.sparta.able.repository;

import com.sparta.able.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueryDsl {
}
