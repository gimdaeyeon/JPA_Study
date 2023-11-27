package com.jpa.task.domain.entity;

import com.jpa.task.domain.embedded.Address;
import com.jpa.task.domain.enumType.UserGender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class UserTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void defaultTest(){
        User user = new User();
        user.setLoginId("aaa");
        user.setPassword("1234");
        user.setName("김철수");
        user.setAddress(new Address("강남구","100호","11111"));
//        user.setUserGender(UserGender.M);

        entityManager.persist(user);
    }


}























