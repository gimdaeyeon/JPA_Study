package com.jpa.basic.repository;

import com.jpa.basic.domain.Member;
import com.jpa.basic.domain.type.MemberGender;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
@Slf4j
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save(){
        Member member = new Member();
        member.setName("홍길동");
        member.setAge(11);
        member.setPassword("1234");
        member.setEmail("bbb@naver.com");
        member.setMemberGender(MemberGender.MALE);

        memberRepository.save(member);
    }
    @Test
    void findOne(){
        Member foundMember = memberRepository.findOne(1L);
        foundMember.setEmail("abcd@naver.com");
    }
    @Test
    void findAll(){
        List<Member> memberList = memberRepository.findAll();

        memberList.forEach(System.out::println);

        memberList.get(0).setPassword("9999");
    }
    @Test
    void delete(){
        Member foundMember = memberRepository.findOne(1L);
        memberRepository.delete(foundMember );
    }


}








