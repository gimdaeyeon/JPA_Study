package com.jpa.jpql.domain2;

import com.jpa.jpql.dto.MemberDto;
import com.jpa.jpql.embedded.Address;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Commit
class MemberTest {

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Address address = new Address("강남구", "1011호", "11122");

        Member member = new Member();
        member.setName("홍길동");
        member.setAddress(address);

        entityManager.persist(member);

        Member member2 = new Member();
        member2.setName("김철수");
        member2.setAddress(new Address("강북구", "102호", "33333"));

        entityManager.persist(member2);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void jpql() {

        String query = "select m from Member m " +
                       "where m.address.address like '%강남구%'";

        List<Member> resultList = entityManager.createQuery(query, Member.class).getResultList();
    }

    @Test
    void nativeQuery() {
        String query = "SELECT *FROM JPA_MEMBER WHERE NAME = '김철수'";
        List<Member> resultList = entityManager.createNativeQuery(query, Member.class).getResultList();
    }

    @Test
    void criteria() {
//        criteria 사용 준비
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        어떤 엔티티를 조회용으로 사용할 것인지 설정한다.
        CriteriaQuery<Member> query = criteriaBuilder.createQuery(Member.class);

//        조회 클래스, from 절을 세팅하고 크리테리아로 쿼리를 만들 때 이 객체를 사용한다.
        Root<Member> m = query.from(Member.class);

//        쿼리 생성하기
        CriteriaQuery<Member> criteriaQuery = query.select(m).where(criteriaBuilder.equal(m.get("name"), "김철수"));

        List<Member> resultList = entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Test
    void queryDsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QMember member = QMember.member;

//        쿼리 생성
        JPAQuery<Member> query = queryFactory.selectFrom(member)
                .where(member.address.address.like("%남%"));

//        쿼리 실행
        List<Member> members = query.fetch();
    }

    @Test
    void jpql2() {
//        createQuery()의 결과는 TypedQuery가 만들어진다.(여태 우리가 사용한 방식)
        String query = "SELECT m FROM Member m where m.name = '김철수'";
        TypedQuery<Member> typedQuery = entityManager.createQuery(query, Member.class);
        List<Member> resultList = typedQuery.getResultList();

//        id만 가져오고 싶은 경우에 select id를 명시하고 id의 타입 Long을 반환타입으로 설정하면 된다.
//        (여기서 select절에 작성한 조회 대상을 프로젝션 이라고 한다.)
        TypedQuery<Long> typedQuery1 = entityManager.createQuery("SELECT m.id FROM Member m WHERE m.name = '김철수'", Long.class);
        List<Long> resultList1 = typedQuery1.getResultList();

//        만약 타입을 설정하지 않거나 그럴수 없는 경우
//        이런 경우 Query타입이 반환된다.
        Query query1 = entityManager.createQuery("SELECT m.id, m.name FROM Member m WHERE m.name ='김철수'");
        List<Object[]> resultList2 = query1.getResultList();

        resultList2.forEach(e->{
            System.out.println(e[0]);
            System.out.println(e[1]);
        });

        resultList2.stream().map(Arrays::toString).forEach(System.out::println);

        String sql = "SELECT new com.jpa.jpql.dto.MemberDto(m.id, m.name) FROM Member m WHERE m.name ='김철수'";

        TypedQuery<MemberDto> query2 = entityManager.createQuery(sql, MemberDto.class);
        List<MemberDto> resultList3 = query2.getResultList();

        assertThat(resultList3.size()).isNotEqualTo(0);

    }


}