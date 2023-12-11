package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.dto.UserDto;
import com.jpa.data01.domain.embedded.Address;
import com.jpa.data01.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

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

        userQueryRepository.saveAll(List.of(user1, user2, user3));
    }

    @Test
    void findUserNames(){
        List<String> userNames = userQueryRepository.findUserNames();
        System.out.println("userNames = " + userNames);
    }
    @Test
    void findUserByPhonePattern(){
        List<User> userByPhonePattern = userQueryRepository.findUserByPhonePattern();
        System.out.println("userByPhonePattern = " + userByPhonePattern);
    }
    @Test
    void findUserByAddressDetail(){
        List<User> userByAddressDetail = userQueryRepository.findUserByAddressDetail("101호");
        System.out.println("userByAddressDetail = " + userByAddressDetail);
    }
    @Test
    void findUserByFirstName(){
        List<User> userByFirstName = userQueryRepository.findUserByFirstName("크");
        System.out.println("userByFirstName = " + userByFirstName);
    }
    @Test
    void findUserByNames(){
        List<User> userByNames = userQueryRepository.findUserByNames(List.of("뽀로로","크롱"));
        System.out.println("userByNames = " + userByNames);
    }
    @Test
    void findUsersAddress(){
        List<Address> usersAddress = userQueryRepository.findUsersAddress();
        System.out.println("usersAddress = " + usersAddress);
    }
    @Test
    void findUserDtoByZipcode(){
        List<UserDto> userDtoByZipcode = userQueryRepository.findUserDtoByZipcode("1");
        System.out.println("userDtoByZipcode = " + userDtoByZipcode);
    }
    @Test
    void findUserMapByAddressGroupBY(){
        Map<String, Object> userMapByAddressGroupBY = userQueryRepository.findUserMapByAddressGroupBY();
        System.out.println("userMapByAddressGroupBY = " + userMapByAddressGroupBY);
    }
    @Test
    void findUserByUserAge(){
        List<User> userByUserAge = userQueryRepository.findUserByUserAge();
        System.out.println("userByUserAge = " + userByUserAge);
    }


}