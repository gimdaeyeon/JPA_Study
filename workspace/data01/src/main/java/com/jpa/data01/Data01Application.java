package com.jpa.data01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // Auditing기능을 사용할 거라고 spring boot에게 알려준다.
public class Data01Application {

    public static void main(String[] args) {
        SpringApplication.run(Data01Application.class, args);
    }

}
