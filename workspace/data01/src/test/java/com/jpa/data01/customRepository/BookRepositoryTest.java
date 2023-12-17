package com.jpa.data01.customRepository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class BookRepositoryTest {
    @Autowired BookRepository bookRepository;

    @Test
    void customTest(){
        bookRepository.findCustomByName("test");
    }




}