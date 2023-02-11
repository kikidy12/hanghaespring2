package com.example.hanghaespring2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeSpring2Application {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeSpring2Application.class, args);
    }

}
