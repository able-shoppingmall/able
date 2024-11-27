package com.sparta.able.repository;

import com.sparta.able.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQueryDsl {
    Optional<Product> findByName(String name);

}
