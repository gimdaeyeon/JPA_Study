package com.jpa.basic3.domain2;

import com.jpa.basic3.domain2.embedded.Address;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class MemberTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        Address address = new Address("강남구","1011호","11122");
//        address.setAddress("강남구");
//        address.setAddressDetail("1011호");
//        address.setZipcode("11122");

        Member member = new Member();
        member.setName("홍길동");
        member.setAddress(address);

        entityManager.persist(member);

        Member member2 = new Member();
        member2.setName("김철수");
        member2.setAddress(new Address("강북구","102호","33333"));

        entityManager.persist(member2);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void save(){

    }

    @Test
    void equals(){
        Address address = new Address("강북구","102호","33333");
        Member member = entityManager.find(Member.class, 2L);

        System.out.println("isTrue? : "+ address.equals(member.getAddress()));

    }




}