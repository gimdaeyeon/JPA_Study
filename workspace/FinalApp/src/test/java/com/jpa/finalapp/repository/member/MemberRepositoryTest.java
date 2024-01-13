package com.jpa.finalapp.repository.member;

import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    Member member;
    @BeforeEach
    void setUp(){
        member = Member.builder()
                .loginId("test")
                .password("1234")
                .memberAddress(new MemberAddress("강남구", "논현동","12345"))
                .birth(LocalDate.of(2000,10,2))
                .name("홍길동")
                .phoneNumber("01011111111")
                .build();
    }

    @Test
    @DisplayName("회원가입, 로그인")
    void saveAndFindId(){
        // given
        memberRepository.save(member);
        // when
        Long fondId = memberRepository.findIdByLoginIdAndPassword(
                    member.getLoginId(),
                    member.getPassword()
                ).orElse(null);
        // then
        assertThat(fondId).isNotNull().isEqualTo(member.getId());
    }




}










