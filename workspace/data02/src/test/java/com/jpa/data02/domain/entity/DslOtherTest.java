package com.jpa.data02.domain.entity;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.jpa.data02.domain.dto.DepartmentDto;
import com.jpa.data02.domain.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.List;

import static com.jpa.data02.domain.entity.QDepartment.department;
import static com.jpa.data02.domain.entity.QEmployee.employee;

@SpringBootTest
@Transactional
@Commit
public class DslOtherTest {
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
    @DisplayName("조회 결과 연결하기")
    void concat() {
        List<String> concatList = queryFactory.select(employee.name.concat(" : ").concat(employee.email))
                .from(employee)
                .fetch();
        System.out.println("concatList = " + concatList);
    }

    @Test
    @DisplayName("문자열이 아닌 값과 연결하기")
    void concat2() {
//        sql 또는 Jpql에서는 concat에 문자열이 아닌 값을 넣어도 자동 형변환된다.
//        그러나 queryDsl은 concat의 매개변수 타입이 String 이므로 문자열을 넣어야 한다.
//        이런 경우 해당 필드에 stringValue()를 사용하여 문자열 값으로 변경해주면 된다.
        JPAQuery<String> concatList = queryFactory
                .select(employee.name.concat(" : ").concat(employee.salary.stringValue()))
                .from(employee);
        System.out.println("concatList = " + concatList);
    }

    @Test
    @DisplayName("case문")
    void caseWhenThen() {
        List<String> list = queryFactory.select(employee.department.name
                        .when("개발").then("야근")// 이 방식을 사용하면 when에는 =조건만 사용 가능하다
                        .when("디자인").then("퇴근")
                        .otherwise("퇴사")//  otherwise는 필수이다
                )
                .from(employee)
                .fetch();
        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("case문2")
    void caseWhenThen2() {
//        같다(=) 조건 외에 여러 조건을 사용하고 싶으면 CaseBuilder를 활용한다.
        StringExpression salaryGrade = new CaseBuilder()
                .when(employee.salary.gt(20_000)).then("부자")
                .when(employee.salary.gt(15_000)).then("평균")
                .when(employee.salary.gt(10_000)).then("거지")
                .otherwise("모름");

        List<String> list = queryFactory.select(salaryGrade)
                .from(employee)
                .fetch();
        System.out.println("list = " + list);
    }

    @Test
    @DisplayName("상수 조회하기")
    void constantSelect() {
        List<Tuple> tupleList = queryFactory.select(employee, Expressions.constant(100))
                .from(employee)
                .fetch();

        tupleList.forEach(tuple -> {
            System.out.println("emp : " + tuple.get(employee));
            System.out.println("constant : " + Expressions.constant(100));
        });
    }

    @Test
    @DisplayName("Dto로 반환받기")
    void returnDto() {
//        jpql
        em.createQuery(
                        "select new org.jpa.data02.domain.dto.EmployeeDto(e.name,e.salary,e.hireDate" +
                        ") from Employee e", Employee.class)
                .getResultList();
//        queryDSL 에서는 프로젝션을 Dto로 반환받기 위해 Projections 타입을 사용한다.
//        Projections로 Dto를 반환받는 방법은 3가지 방법이 있다.

//        1. fields() : 필드에 직접 접근해서 Dto를 초기화시켜준다.
//        - 필드에 직접 근하므로 프로젝션 순서, 개수 상관없이 반환받을 수 있다.
//        - 생성자나 setter 없어도 Dto를 반환 받을 수 있다.
//        - 필드가 private이여도 상관이 없다. (리플렉션을 사용하기 때문)
//        단, QueryDSL이 Dto객체를 생할 때 기본생성자를 사용하므로 Dto에 기본생성자는 필수이다.
        List<EmployeeDto> empList = queryFactory.select(Projections.fields(EmployeeDto.class, employee.name, employee.hireDate)
                ).from(employee)
                .fetch();

        System.out.println("empList = " + empList);

//        2. bean() : setter로 접근
//        - setter를 사용하므로  프로젝션의 순서, 개수 상관없이 프로젝션을 선택할 수 있다.
//        - 기본 생성자가 필수이다.
        List<EmployeeDto> empList2 = queryFactory.select(
                        Projections.bean(EmployeeDto.class, employee.name, employee.salary)
                )
                .from(employee)
                .fetch();
        System.out.println("empList2 = " + empList2);

//        3. constructor() : 생성자로 초기화
//        - 생성자를 상요하므로 dto에 만들어놓은 생성자의 파라미터 순서와 개수에 맞게 전달해야 한다.
//        기본 생성자가 필요없다.(근데 어차피 dto를 빈등록해서 사용하려면 기본생성자를 만들어야한다.)
        List<EmployeeDto> empList3 = queryFactory.select(
                        Projections.constructor(EmployeeDto.class,
                                employee.name, employee.salary, employee.hireDate)
                )
                .from(employee)
                .fetch();
        System.out.println("empList3 = " + empList3);
    }
    @Test
    @DisplayName("Dto 리턴받기2")
    void returnDto2(){
//        만약 엔티티의 필드명과 Dto의 필드명이 다르다면?
//        List<DepartmentDto> deptList = queryFactory.select(
//                        Projections.bean(DepartmentDto.class,
//                                department.name, department.officeLocation, department.phone)
//                )
//                .from(department)
//                .fetch();
//        System.out.println("deptList = " + deptList);
//        확인해보면 필드명이 일치하지 않아서 데이터가 바인딩되지 않는것을 확인할 수 있다.

//        이 경우 마이바티스와 동일하게 별칭을 활요하면 된다.
        List<DepartmentDto> deptList = queryFactory.select(
                        Projections.bean(DepartmentDto.class,
                                department.name, department.officeLocation.as("office"), department.phone.as("phoneNumber"))
                )
                .from(department)
                .fetch();
        System.out.println("deptList = " + deptList);
    }


}



















