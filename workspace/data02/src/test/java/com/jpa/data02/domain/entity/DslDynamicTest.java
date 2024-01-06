package com.jpa.data02.domain.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
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

@SpringBootTest
@Transactional @Commit
public class DslDynamicTest {
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
    @DisplayName("jpql 동적 쿼리")
    void jpqlDynamicQuery(){
//        jqpl을 직접 사용하는 경우 동적 쿼리

//        아래 변수는 매개변수로 받았다고
        String searchType = "name";
        String keyword = "김철수";

        String jpql = "select e from Employee e";

        if(searchType.equals("name")){
            jpql += " where e.name = :keyword";
        } else if(searchType.equals("salary")) {
            jpql += " where e.salary = :keyword";
        }

        List<Employee> empList = em.createQuery(jpql, Employee.class)
                .setParameter("keyword", keyword)
                .getResultList();
        System.out.println("empList = " + empList);

//        이런 동적쿼리 생성은 문자열을 기반으로 만들어지기 때문에 오류를 찾기 힘들고 가독성이 떨어진다.
    }
    @Test
    @DisplayName("QueryDSL 동적쿼리01")
    void dslDynamicQuery(){
        String searchType = "name";
        String keyword = "김철수";
        LocalDate date = LocalDate.of(2000,1,2);

//        queryDSL로 동적 쿼리를 만들 때 2가지 방법을 사용한다.
//        1. BooleanBuilder를 사용한다.
//        BooleanBuilder는 where절에 사용할 조건을 동저긍로 만들어줄 수 있다.
//        and(), or() 를 사용하여 쿼리를 생성한다.

        BooleanBuilder builder = new BooleanBuilder();

        QEmployee employee = QEmployee.employee;

        if("name".equals(searchType)){
            builder.and(employee.name.eq(keyword));
        }else if("salary".equals(searchType)){
            builder.and(employee.salary.eq(Integer.parseInt(keyword)));
        }

        if (date !=null){
            builder.and(employee.hireDate.after(date));
        }

        List<Employee> empList = queryFactory.selectFrom(employee)
                .where(builder.not())
                .fetch();
        System.out.println("empList = " + empList);
    }
    @Test
    @DisplayName("QueryDSL 동적쿼리02")
    void dslDynamicQuery02(){
        String searchType = "name";
        String keyword = "김철수";
        LocalDate date = LocalDate.of(2000,1,2);

//        queryDSL로 동적 쿼리를 만들 때 2가지 방법을 사용한다.
//        1. BooleanBuilder를 사용한다.
//        BooleanBuilder는 where절에 사용할 조건을 동저긍로 만들어줄 수 있다.
//        and(), or() 를 사용하여 쿼리를 생성한다.

        QEmployee employee = QEmployee.employee;

//        동적 쿼리 조건 생성 코드가 복잡하거나 반복적으로여러 코드에서 사용된다면
//        메소드로 분리하여 가독성과 유지보수성을 높일 수 있다.
        List<Employee> empList = queryFactory.selectFrom(employee)
//                .where(searchCond1(searchType,keyword,date))
                .where(searchCond1(null,null,null))
                .fetch();
        System.out.println("empList = " + empList);
    }

    BooleanBuilder searchCond1(String searchType, String keyword, LocalDate date){
        BooleanBuilder builder = new BooleanBuilder();

        QEmployee employee = QEmployee.employee;

        if("name".equals(searchType)){
            builder.and(employee.name.eq(keyword));
        }else if("salary".equals(searchType)){
            builder.and(employee.salary.eq(Integer.parseInt(keyword)));
        }

        if (date !=null){
            builder.and(employee.hireDate.after(date));
        }
        return builder;
    }

//    실습
//    name과 officeLocation을 입력받아 부서를 조회한다.
//    name또는 officeLocation이 일치하는 부서를 조회한다. 둘 중 하나라도 null이면 조건을 생략한다.
    @Test
    @DisplayName("실습")
    void dynamicQueryTask1(){
        String name = "개발";
        String officeLocation = "A";

        QDepartment department = QDepartment.department;
        List<Department> deptList = queryFactory.selectFrom(department)
                .where(departmentSearchCond(name, null))
                .fetch();

        System.out.println("deptList = " + deptList);
    }

    BooleanBuilder departmentSearchCond(String name, String officeLocation){
        BooleanBuilder builder = new BooleanBuilder();

        if (name==null ||officeLocation==null){
            return null; // 쿼리 팩토리에 where()에 null이 들어가면 해당 조건은 생략된다.
        }

        return builder.or(QDepartment.department.name.eq(name))
                .or(QDepartment.department.officeLocation.eq(officeLocation));
    }
    @Test
    @DisplayName("QueryDSL 동적쿼리03")
    void dslDynamicQuery03(){
        String searchType = "name";
        String keyword = "김철수";
        LocalDate date = LocalDate.of(2000, 1, 2);

//        2. where에서 , 를 사용하면 여러 조건을 and로 연결해준다.
//        where(조건1, 조건2) -> 조건1 and 조건2
//        where에 넘긴 조건이 null인 경우 null을 무시한다는 특징이 있다.
//        이 특징을 이요하면 동적으로 조건을 생성할 수 있다.

        queryFactory.selectFrom(QEmployee.employee)
                .where(searchCond2(searchType,keyword),
                        date==null?null: QEmployee.employee.hireDate.after(date))
                .fetch();
    }

    Predicate searchCond2(String searchType, String keyword){
        if(searchType==null || keyword == null){
            return null;
        }
        if(searchType.equals("name")){
            return QEmployee.employee.name.eq(keyword);
        }
        return QEmployee.employee.salary.eq(Integer.parseInt(keyword));
    }

//    실습2
//    사원의 이름, 부서 이름, 부서 전화번호를 전달받아 모두 일치하는 사원이름, 해당 사원의 소속된 부서 정보를 조회
//    모든 조건은 부분일치 조건으로 사용한다.
//    부서 전화번호 : 2-> 2를 포함하는 조건
//    만약 null인 경우 해당 조건은 생략
//    BooleanBuilder를 사용하지 않는다.
    @Test
    @DisplayName("동적쿼리 실습2")
    void dynamicQueryTask2(){
        String empName = "김철";
        String deptName = "개발";
        String deptPhone = null;

        List<Tuple> list = queryFactory.select(QEmployee.employee.name, QEmployee.employee.department)
                .from(QEmployee.employee)
                .where(
                        empNameContains(empName),
                        deptName==null?null: QEmployee.employee.department.name.contains(deptName),
                        deptPhone==null?null: QEmployee.employee.department.phone.contains(deptPhone))
                .fetch();

        list.forEach(tuple -> {
            System.out.println("empName = " + tuple.get(QEmployee.employee.name));
            System.out.println("dept = " + tuple.get(QEmployee.employee.department));
        });
    }

    Predicate empNameContains(String name){
        return name==null?null: QEmployee.employee.name.contains(name);
    }



}























