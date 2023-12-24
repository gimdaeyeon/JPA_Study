package org.jpa.data02.doamin.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;

@SpringBootTest
@Transactional@Commit
public class DslJoinTest {
    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);

        Department dept1 = Department.builder()
                .name("개발")
                .phone("02-1111-1111")
                .officeLocation("A")
                .build();

        Department dept2 = Department.builder()
                .name("디자인")
                .phone("02-2222-2222")
                .officeLocation("B")
                .build();

        em.persist(dept1);
        em.persist(dept2);

        Employee employee1 = Employee.builder()
                .name("김철수")
                .salary(10_000)
                .hireDate(LocalDate.of(2000, 10, 10))
                .email("test@naver.com")
                .department(dept1)
                .build();

        Employee employee2 = Employee.builder()
                .name("홍길동")
                .salary(20_000)
                .hireDate(LocalDate.of(2010, 1, 1))
                .email("hong@naver.com")
                .department(dept1)
                .build();

        Employee employee3 = Employee.builder()
                .name("이지웅")
                .salary(18_000)
                .hireDate(LocalDate.of(2020, 7, 22))
                .email("woong@naver.com")
                .department(dept2)
                .build();

        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
    }

    @Test
    @DisplayName("기본 join")
    void basicJoin(){

    }









}
