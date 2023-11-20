package com.jpa.basic3.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
@Slf4j
class EntityTest {
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void save(){
        Clothes clothes = new Clothes();
        clothes.setName("옷");
        clothes.setPrice(20000);
        clothes.setColor("red");
        clothes.setSize(100);

        entityManager.persist(clothes);

        Book book = new Book();
        book.setName("책");
        book.setPrice(15_000);
        book.setAuthor("홍길동");
        book.setPublisher("코리아IT");
        entityManager.persist(book);
    }

    @Test
    void find(){
        Book book = entityManager.find(Book.class, 2L);

        System.out.println("book = " + book.getName());
    }

    @Test
    void update(){
        Book book = entityManager.find(Book.class, 2L);
        book.setAuthor("박웅이");
    }

    @Test
    void delete(){
        Book book = entityManager.find(Book.class, 2L);
        entityManager.remove(book);
    }

}
















