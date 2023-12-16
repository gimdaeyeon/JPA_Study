package com.jpa.data01.dataRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class CheckOutQueryRepositoryTest {
//
    @Autowired
    CheckOutQueryRepository checkOutQueryRepository;

    @Test
    void checkOutQueryRepository(){
        List<Map<String, Object>> usersCheckOutBook = checkOutQueryRepository.findUsersCheckOutBook();
        System.out.println("usersCheckOutBook = " + usersCheckOutBook);
    }





}












