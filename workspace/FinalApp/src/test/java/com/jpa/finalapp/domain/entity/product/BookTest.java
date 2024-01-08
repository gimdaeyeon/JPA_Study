package com.jpa.finalapp.domain.entity.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BookTest {

    @Test
    void bookTest(){
        Book book  = Book.builder()
                .id(1L)
                .author("김철수")
                .price(10000)
                .quantity(100)
                .build();

        System.out.println("book = " + book);
    }




}











