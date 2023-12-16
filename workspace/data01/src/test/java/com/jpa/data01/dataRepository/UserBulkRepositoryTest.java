package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.SortComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class UserBulkRepositoryTest {
    @Autowired UserBulkRepository userBulkRepository;

    @Test
    void test1(){
        userBulkRepository.userModify1();

        List<User> userList = userBulkRepository.findAll();
        userList.stream()
                .filter(user->user.getAddress().getAddress().equals("강남구"))
                .forEach(System.out::println);

    }
    @Test
    void test2(){
        userBulkRepository.userModify2();

        List<User> modify = userBulkRepository.findAll();
        System.out.println("modify = " + modify);

    }




}