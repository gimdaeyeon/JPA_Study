package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.entity.CheckOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CheckOutDataRepository extends JpaRepository<CheckOut,Long> {
//    연관관계를 맺은 엔티티로 조회(해당 엔티티의 ID를 이용하여 조회함)
    Optional<CheckOut> findByBook(Book book);

//    엔티티의 id로 조회가능
    Optional<CheckOut> findByBookId(Long bookId);

//  연관관계를 맺은 엔티티의 ID를 제외한 다른 필드로 조회를 하면 join을 사용할 수 밖에 없다.
    Optional<CheckOut> findByBookName(String bookName);

//    =============================================================================
//  @Query를 사용할 때에도 일반 join을 사용하면 지연로딩으로 인한 N+1문제가 동일하게 발생된다.
    @Query("select ch from CheckOut ch join ch.book")
    List<CheckOut> findByJoinBook();

//    fetch join을 이용한 N+1 문제 해결
    @Query("select ch from CheckOut ch join fetch ch.book")
    List<CheckOut> findByFetchJoinBook();

//    일반적으로 연관관계를 맺은 엔티티를 join하여 조회한다.(여러 개 가능)
//    outer join이 가능하며 left, right를 활용하면 된다.
    @Query("select ch from CheckOut ch right join fetch ch.book join fetch ch.user")
    List<CheckOut> findByFetchJoinBookAndUser();

//    연관관계가 있는 엔티티틀 join할 때 on절을 추가하면 기존의 id조건에 새로운 조건을 and로 연결한다.
    @Query("select ch from CheckOut ch join ch.book on ch.book.category = 'IT'")
    List<CheckOut> findByFetchJoinBookOn();

//    만약 연관관계와 무관한 값으로 조인을 한다면? on절을 직접 명시를 해야한다.
//    이렇게 무관한 값을 join하면 fetch가 적용되지 않는다.
//    @Query("select ch from CheckOut ch join fetch Book b on ch.deleted = b.deleted")

//    이 경우 프로젝션을 정확히 명시하여 조회하면 된다.
    @Query("select ch,b from CheckOut ch join Book b on ch.deleted = b.deleted")
    List<CheckOut> joinTest1();

//    Spring Data JPA에서 제공하는 어노테이션
//    이름으로 쿼리를 만들거나 기본제공되는 메소드를 재정의하여 사용할 때
//    jpql없이 fetch join을 사용할 수 있다. left join을 사용한다.
    @Override @EntityGraph(attributePaths = {"book"})
    List<CheckOut> findAll();

    @EntityGraph(attributePaths = {"book","user" })
    List<CheckOut> findByCreatedDateGreaterThan(LocalDateTime dateTime);

//    조인과 페이징 처리
//    페이징 처리에서 반드시 필요한 쿼리는 total쿼리이다.
//    left outer join의 결과 total을 구할 때는 join이 필요 없다. (상황에 따라 다름)
//    이런 경우 total쿼리에 join이 사용되면 성능 저하의 원인이 되므로 count쿼리를 따로 작성하는게 좋다.
//    spring data jpa가 성능 최적화를 어느정도 도와주지만 쿼리를 확인하고 수정해야 한다면
//    countQuery 속성을 사용하여 설정할 수 있다.
    @Query(value = "select ch from CheckOut ch left join ch.book",
        countQuery = "select count(ch.id)from CheckOut ch")
    Page<CheckOut> joinTest3(Pageable pageable);
//===========================================================
//    jpql의 서브쿼리는 where, having에서만 사용이 가능했다.
//    하이버네이트에서는 select절에서도 사용 가능
//    from절 서브쿼리는 하이버네이트 6.1 이상부터 지원한다.

    @Query("""
        select ch from CheckOut ch where ch.user.birth = (
            select min(u.birth) from User u
    )
""")
    List<CheckOut> sub1();

    @Query("""
        select new Map(ch as check,(select 1) as number) 
        from CheckOut ch where ch.id  = 1
""")
    Map<String,Object> sub2();

    @Query("""
            select s.id from(
                select ch.id as id from CheckOut ch where ch.id =1           
            ) s
      """)
    Optional<Long> sub3();

//    book 평균 가격보다 높은 책의 대여 기록을 조회하기
    @Query("""
        select ch from CheckOut ch 
        where ch.book.price > (
            select avg(b.price) from Book b
        )
""")
    List<CheckOut> task1();

//    book 카테고리별 가격이 가장 높은 책의 대여 기록을 조회하기
    @Query("""
        select ch from CheckOut ch
        where ch.book.price in(
            select max(b.price) from Book b
            group by b.category
        )
    """)
    List<CheckOut> task2();






}

























