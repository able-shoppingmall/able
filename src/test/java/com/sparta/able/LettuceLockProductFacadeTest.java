package com.sparta.able;

import com.sparta.able.entity.Owner;
import com.sparta.able.entity.Product;
import com.sparta.able.enums.Category;
import com.sparta.able.exception.ApplicationException;
import com.sparta.able.exception.ErrorCode;
import com.sparta.able.redis.LettuceLockFacade;
import com.sparta.able.repository.OwnerRepository;
import com.sparta.able.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LettuceLockProductFacadeTest {
    @Autowired
    private LettuceLockFacade lettuceLockProductFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    // 각 테스트가 실행되기 전에 데이터베이스에 테스트 데이터 생성
    @BeforeEach
    public void before() {
        Owner owner = ownerRepository.findById(1L).orElseThrow(null);
        Product product = new Product();
        product.setOwner(owner);
        product.setName("test");
        product.setPrice(1000);
        product.setCategory(Category.DEFAULT);
        product.setAmount(100);
        product.setCreatedAt(LocalDateTime.now());
        product.setModifiedAt(LocalDateTime.now());

        productRepository.saveAndFlush(product);
    }

    // 각 테스트를 실행한 후에 데이터베이스에 테스트 데이터 삭제
    @AfterEach
    public void after() {
        productRepository.deleteAll();
    }

    @Test
    public void 동시에_100개의_요청() throws InterruptedException {

        Product product = productRepository.findByName("test").orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_PRODUCT)
        );

        Long id = product.getId();

        // when
        // 100개의 쓰레드 사용(멀티스레드)
        int threadCount = 100;

        // ExecutorService : 비동기로 실행하는 작업을 간단하게 실행할 수 있도록 자바에서 제공하는 API
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // CountDownLatch : 작업을 진행중인 다른 스레드가 작업을 완료할때까지 대기할 수 있도록 도와주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);
        // 100개의 작업 요청
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lettuceLockProductFacade.decrease(Product.class, id, 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        product = productRepository.findByName("test").orElseThrow(
                () -> new ApplicationException(ErrorCode.NOT_FOUND_PRODUCT)
        );

        assertEquals(0, product.getAmount());
    }
}