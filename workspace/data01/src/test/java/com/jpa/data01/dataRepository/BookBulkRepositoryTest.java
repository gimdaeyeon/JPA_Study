package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class BookBulkRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired BookBulkRepository bookBulkRepository;

    @Test
    void test(){
//        jpa에서 변경 감지를 통새 수정을 할 수 있다.
        Book book = em.find(Book.class, 1L);
        book.setName("수정한다.");
        
//        만약 수정할 데이터가 많다면?
        List<Book> booklilList = bookBulkRepository.findAll();
        booklilList.forEach(b->b.setName("수정한다"));

//        확인해보니 100권의 책에 update쿼리를 각각 날리고 있다.
//        즉, 기존의 방식으로 여러건의 데이터를 수정하게 되면
//        1. N개의 데이터를 모두 영속 컨텍스트로 가져온다.
//        2. N개의 데이터를 setter로 모두 수정한다.
//        3. flush 시점에 N개의 변경감지가 일어나며 N개의 SQL문이 실행된다.
//        매우 매우 비효율적이다.
    }
    @Test
    void bulkUpdate(){
//        벌크 연산(벌크성 쿼리)이란 여러 행의 데이터를 하나의 쿼리로 수정, 삭제하는 것을 의미한다.
        em.createQuery("update Book b set b.name = '수정했다.'")
                .executeUpdate();   // 수정, 삭제 쿼리는 executeUpdate()를 사용해야한다.
//        확인해보면 하나의 쿼리만 실행되었다.
    }
    @Test
    void bulkDelete(){
//        삭제도 동일한 방식으로 진행한다.
//        오류가 발생하는데 확인해보니 벌크 delete쿠리가 날아가고 있다.
//        벌크 연산은 jpql을 사용하여 db에 쿼리를 즉시 만들어 보낸다.
//        즉, 엔티티 관리와 무관하게 쿼리를 날리게 되므로 soft delete가 수행되지 않는 것이다.
//        cascade옵션도 적용되지 않는다. 그러므로 연관관계를 맺은 데이터를 직접 삭제해야한다.
//        em.createQuery("delete Book b where b.id > 50")
//                .executeUpdate();

        em.createQuery("update CheckOut ch set ch.book.id = null where ch.book.id>50")
                .executeUpdate();

        em.createQuery("delete Book b where b.id > 50")
                .executeUpdate();
    }
    @Test
    void bulkTest(){
//        벌크 연산에서 가장 주의해야하는 것은 데이터 불일치 문제이다.
        Book foundBook = em.find(Book.class, 1L);
        System.out.println("foundBook = " + foundBook);

//        조회 직후 벌크 연산으로 데이터를 수정한다.
        em.createQuery("update Book b set b.name = '수정완료' where b.id<10")
                .executeUpdate();

//        처음 조회한 1번 책도 수정 대상에 속하므로 DB에 저장된 name이 수정되었을 것이다.
//        그러나 영속화된 1번 책의 엔티티는 name이 수정되지 않았다.(DB와 엔티티간의 데이터 불일치)
        Book foundBook2 = em.find(Book.class, 1L);
        System.out.println("foundBook2 = " + foundBook2);
    }
    @Test
    void bulkTest2(){
        Book foundBook = em.find(Book.class, 1L);
        System.out.println("foundBook = " + foundBook);

        em.createQuery("update Book b set b.name = '수정완료' where b.id<10")
                .executeUpdate();

//        불일 치 문제를 해결하기 위해서는
//        1. refresh : 영속 컨텍스트에 존재하는 특정 엔티티를 DB에서 다시 조회한다.
//        em.refresh(foundBook);
//        2. 항상 벌크연산을 먼저 실행 : 가능한 상황이라면 제일 무난함
//        3. 영속 컨텍스트를 비우기 : 전부 비우고 새로 읽어 온다.
        em.clear();

        Book foundBook2 = em.find(Book.class, 1L);
        System.out.println("foundBook2 = " + foundBook2);
    }


}