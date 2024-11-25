
package com.sparta.able;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AbleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbleApplication.class, args);
    }

}