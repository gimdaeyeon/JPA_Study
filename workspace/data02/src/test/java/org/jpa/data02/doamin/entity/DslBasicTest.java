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
import java.util.List;

import static org.jpa.data02.doamin.entity.QEmployee.employee;

/*
    QueryDsl
    JPQL을 생성해주는 기술(오픈소스 프레임워크)
    문자열이 아닌 메소드 체이닝 방식으로 JPQL을 만들 수 있기 때문에 문법 오류를
    컴파일 단계에서 확인할 수 있으며, 직관적인 동적쿼리를 작성할 수 있다는 장점이 있다.
    QueryDsl은 실제 엔티티가 아닌 엔티티의 정보를 담은 Q타입 객체를 사용하며, Q타입 클래스는 설정된 특정 위치에 생성된다.

    표준 기술이 아니지만 JPA를 사용한다면 필수적으로 가져가는 기술이다.
    표준이 아니기 때문에 설저으이 번거로움과 버전 변경시 새로운 설정 방법을 찾아야한다는 단점이 있다.
 */
@SpringBootTest
@Transactional
@Commit
public class DslBasicTest {

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
                .phone("02-222-2222")
                .officeLocation("B")
                .build();

        em.persist(dept1);
        em.persist(dept2);

        Employee employee1 = Employee.builder()
                .name("김철수")
                .salary(10_000)
                .hireDate(LocalDate.of(2000, 10, 10))
                .email("test@navver.com")
                .department(dept1)
                .build();

        Employee employee2 = Employee.builder()
                .name("홍길동")
                .salary(20_000)
                .hireDate(LocalDate.of(2000, 1, 1))
                .email("hong@naver.com")
                .department(dept1)
                .build();

        Employee employee3 = Employee.builder()
                .name("이지웅")
                .salary(10_000)
                .hireDate(LocalDate.of(2000, 1, 22))
                .email("woong@naver.com")
                .department(dept2)
                .build();

        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);
    }

    @Test
    @DisplayName("QueryDSL 설정 테스트")
    void loadTest() {
//        QueryDSL을 사용하여 jpql을 생성하기 위해서는 JPAQueryFactory 객체가 필요하며
//        entityManager를 넘겨줘야한다.
        QEmployee qEmployee = employee;

        List<Employee> empList = queryFactory.select(qEmployee).from(qEmployee).fetch();

        empList.forEach(System.out::println);
    }
//    =======================================================================================
    @Test
    @DisplayName("Q타입 생성하기")
    void createQType(){
//        1. 별칭 지정하여 생성
//        QEmployee qEmployee = new QEmployee("e");
//        2. 기본 객체 사용
//        QEmployee qEmployee2 = QEmployee.employee;

        QEmployee qEmployee = employee;

    }



}























