package com.jpa.task.repository;

import com.jpa.task.domain.embedded.Address;
import com.jpa.task.domain.entity.User;
import com.jpa.task.domain.enumType.UserGender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;
    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("홍길동");
        user.setLoginId("aaa");
        user.setPassword("1234");
        user.setUserGender(UserGender.M);
        user.setAddress(new Address("강남구","100호","12343"));

        userRepository.save(user);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void save(){
    }
    @Test
    @DisplayName("식별자로 조회")
    void findById(){
        Optional<User> founduser = userRepository.findById(1L);

        User user = founduser.orElseThrow(() -> new IllegalStateException("조회 결과 없음"));

        assertThat(user.getLoginId()).isEqualTo("aaa");
    }
    @Test
    @DisplayName("아이디로 조회")
    void findByLoginId(){
        Optional<User> foundUser = userRepository.findByLoginId("aaa");
        foundUser.ifPresentOrElse(
                user->assertThat(user.getName()).isEqualTo("홍길동"),
//                fail이 실행되면 해당테스트 실패
                ()->fail("테스트 실패")
        );
    }
    @Test
    @DisplayName("로그인 처리")
    void findByLoginIdAndPassword(){
        Optional<Long> foundId = userRepository.findByLoginIdAndPassword("aaa", "1234");

        foundId.ifPresentOrElse(
                id->assertThat(id).isEqualTo(4L),
                ()->fail("테스트 실패")
        );

    }





}