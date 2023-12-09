package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.embedded.Address;
import com.jpa.data01.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
class UserDataRepositoryTest {
    @Autowired
    UserDataRepository userDataRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Address address1 = new Address("강남구", "101호", "11111");
        Address address2 = new Address("송파구", "202호", "22222");
        Address address3 = new Address("노원구", "303호", "33333");


        User user1 = new User();
        user1.setName("뽀로로");
        user1.setBirth(LocalDate.of(2000, 1, 1));
        user1.setPhone("01011111111");
        user1.setAddress(address1);

        User user2 = new User();
        user2.setName("루피");
        user2.setBirth(LocalDate.of(2010, 10, 19));
        user2.setPhone("01022222222");
        user2.setAddress(address2);

        User user3 = new User();
        user3.setName("크롱");
        user3.setBirth(LocalDate.of(2013, 5, 30));
        user3.setPhone("01033333333");
        user3.setAddress(address3);

        userDataRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    void 핸드폰_번호로_조회() {
        List<User> byPhone = userDataRepository.findByPhone("01033333333");
        System.out.println("byPhone = " + byPhone);
    }
    @Test
    void 생일이_특정_날짜보다_이전인_회원_조회(){
        List<User> byBirthBefore = userDataRepository.findByBirthBefore(LocalDate.of(2002, 1, 1));
        System.out.println("byBirthBefore = " + byBirthBefore);
    }
    @Test
    void 이름에_특정_글자가_포함되는_회원_조회(){
        List<User> users = userDataRepository.findByNameContaining("뽀");
        System.out.println("users = " + users);
    }
    @Test
    void 이름에_특정_글자가_포함되는_회원의_수를_조회(){
        long l = userDataRepository.countByNameContaining("뽀");
        System.out.println("l = " + l);
    }
    @Test
    void 특정_핸드폰_번호가_존재하는지_검사(){
        boolean b = userDataRepository.existsByPhone("01033333333");
        System.out.println("b = " + b);
    }
    @Test
    void 이름에_특정_글자가_포함되고_핸드폰에_특정_글자가_포함되는_회원들을_id_내림차순으로_정렬하여_조회(){
        List<User> users = userDataRepository.findByNameContainingAndPhoneContainingOrderByIdDesc("크", "010");
        System.out.println("크 = " + users);
    }
    @Test
    void 여러_이름을_넘겨받아_해당_이름과_일치하는_User_전체_정보_모두_조회하기(){
        List<User> byNameIn = userDataRepository.findByNameIn(List.of("크롱", "루피"));
        System.out.println("byNameIn = " + byNameIn);
    }
    @Test
    void birth가_null이_아니고_id가_특정_범위안에_속하는_회원_모두_조회(){
        List<User> byBirthNotNullAndIdBetween = userDataRepository.findByBirthNotNullAndIdBetween(1L, 3L);
        System.out.println("byBirthNotNullAndIdBetween = " + byBirthNotNullAndIdBetween);
    }


}