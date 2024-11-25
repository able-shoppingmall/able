package com.sparta.able.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.able.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sparta.able.entity.QProduct.*;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryQueryDslImpl implements ProductRepositoryQueryDsl {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> findAllToKeyword(Pageable pageable, String keyword) {
        List<Product> foundProducts = jpaQueryFactory
                .selectFrom(product)
                .where(containKeywordToName(keyword))
                .fetch();

        return new PageImpl<>(foundProducts, pageable, foundProducts.size());
    }

    private BooleanExpression containKeywordToName(String keyword) {
        return StringUtils.hasText(keyword) ? product.name.contains(keyword) : null;
    }
}
