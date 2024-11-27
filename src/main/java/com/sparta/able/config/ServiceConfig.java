package com.sparta.able.config;

import com.sparta.able.entity.Coupon;
import com.sparta.able.entity.Product;
import com.sparta.able.redis.LockableService;
import com.sparta.able.service.CouponService;
import com.sparta.able.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServiceConfig {

    @Bean
    public Map<Class<?>, LockableService<?>> serviceMap(
            ProductService productService,
            CouponService couponService) {
        Map<Class<?>, LockableService<?>> serviceMap = new HashMap<>();
        serviceMap.put(Product.class, productService);
        serviceMap.put(Coupon.class, couponService);
        return serviceMap;
    }
}