package com.jpa.jpql.domain2;

import com.jpa.jpql.dto.MemberDto;
import com.jpa.jpql.embedded.Address;
import com.querydsl.core.types.Projections;
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
import org.junit.jupiter.api.DisplayName;
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

        Board board = new Board();
        board.setTitle("test Title");
        board.setContent("test Content");
        board.setMember(member);

        entityManager.persist(board);

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
    @Test
    @DisplayName("join test")
    void jpql3(){
        String query1 = "SELECT b FROM Board b JOIN b.member m WHERE m.name = :name";
//        join을 사용하면 자동으로 fk,pk가 일치하는 조건의 on절이 생성된다.
//        getResultList()는 조회 결과가 0,1,2이상 이여도 상관 없으나,
//        getSingleResult()는 조회결과가 반드시 1개여야 한다. 0건이거나 2건 이상이면 예외가 발생한다.
        Board board = entityManager.createQuery(query1, Board.class)
                .setParameter("name", "홍길동")
                .getSingleResult();

//        외부 조인
        String query2 = "SELECT b FROM Board b LEFT JOIN b.member m WHERE m.name = :name";
        List<Board> resultList = entityManager.createQuery(query2, Board.class)
                .setParameter("name", "홍길동")
                .getResultList();

//        직접 on절 사용하기
//        실별자 확인 조건은 유지되고, on절에 작성한 조건이 추가된다!!
        String query3 = "SELECT b FROM Board b JOIN b.member m ON m.name = b.content";
        List<Board> resultList1 = entityManager.createQuery(query3, Board.class).getResultList();
    }
    @Test @DisplayName("fetch조인 테스트")
    void fetchTest(){
//        지연로딩 처리가 되어있기 때문에 실제 Member를 사용하는 시점에서 별도의 select를 실행한다.
//        Board foundBoard = entityManager.find(Board.class, 1L);
//        fetch조인 ****(N+1 문제를 해결해준다.)
//        JPQL에서 성능 최적화를 위해 제공하는 조인이다.(SQL에는 없음)
//        일반적인 조인은 쿼리가 실행되는 순간 모든 데이터를 가져와 영속성 컨텍스트에 엔티티를 등록하게 된다.
//        지연 로딩은 해당 객체를 사용하는 시점에 조회하여 성능을 향상시킨다고 했었다.
//        이런 지연로딩을 무시하고 한번에 연관된 entity를 가져와 영속화시키는 방법이 패치 조인이다.
        String query1 = "SELECT b FROM Board b JOIN FETCH b.member m";
        List<Board> resultList = entityManager.createQuery(query1, Board.class).getResultList();
        Board foundBoard = resultList.get(0);
        foundBoard.getMember().getName();
    }
    @Test
    void queryDsl2(){
//        queryDSL은 개발자가 entity로 설정한 클래스들을 Q타입으로 자동 생성한다.
//        Q타입을 이용하여 쿼리를 작성할 수 있다.
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QMember member = QMember.member;    //기본으로 만들어주는 객체 사용
        QBoard board = QBoard.board;

        List<Member> members = queryFactory.select(member)
                .from(member)
                .join(member.boards, board)
                .fetch();

//        Q타입 생성하는 방법2
        QMember member2 = new QMember("m"); // 직접 만들어 사용하기(별칭을 지정할 수 있다.)
        QBoard board2 = new QBoard("b");    // 이 별칭은 내부적으로 사용되므로 신경쓸 필요 없다.

        List<Member> members1 = queryFactory.select(member2)
                .from(member2)
                .join(member2.boards, board2)
                .where(member2.id.gt(0).and(member2.name.eq("홍길동")))
                .orderBy(member2.id.desc())
                .fetch();

//        페이징 처리
//        offset과 limit를 활용하면 페이징 처리가 매우 쉽다.
        queryFactory.select(member2)
                .from(member2)
                .join(member2.boards, board2).fetchJoin()
                .orderBy(member2.id.desc())
                .offset(0).limit(10)    //offset : 몇 번째 결과부터 가져올지 설정, limit : 몇 개 가져올지 설정
                .fetch();
    }
    @Test
    void queryDslTest3(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        QMember member = QMember.member;

//        조회 대상(프로젝션)이 1개인 경우
        List<String> names = queryFactory.select(member.name)
                .from(member)
                .where(member.address.addressDetail.eq("100"))
                .fetch();

//        프로젝션이 여러개인 경우 Dto를 사용한다.
//        1. Dto의 setter를 사용하는 방법
        List<MemberDto> memberDtos = queryFactory.select(
                Projections.bean(MemberDto.class, member.id, member.name)
        ).from(member).fetch();

        memberDtos.forEach(System.out::println);
//        2. Dto의 생성자를 사용하는 방법
        List<MemberDto> fetch = queryFactory.select(
                Projections.constructor(MemberDto.class, member.id, member.name)
        ).from(member).fetch();


    }



}















