package com.jpa.data01.dataRepository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.entity.CheckOut;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional@Commit
class CheckOutDataRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired CheckOutDataRepository checkOutDataRepository;

    @Test
    void findByBook(){
        Book book = em.find(Book.class, 1L);
        Optional<CheckOut> foundCh = checkOutDataRepository.findByBook(book);
        foundCh.ifPresent(System.out::println);
    }
    @Test
    void findByBookId(){
        Optional<CheckOut> foundCh = checkOutDataRepository.findByBookId(1L);
        foundCh.ifPresent(System.out::println);
    }
    @Test
    void findByBookName(){
        Optional<CheckOut> foundCh = checkOutDataRepository.findByBookName("Greenlam");
        foundCh.ifPresent(System.out::println);

//        join을 사용되었으나 Book엔티티를 사용하게 되면 다시 select가 실행됨
//        Book엔티티는 영속화되지 않았기 때문이다.
//        해당 쿼리는 book을 조회하는 것이 아니라 조건으로 사용하기 위한 join이기 때문이다.
        System.out.println(foundCh.get().getBook());
    }
    @Test
    void find(){
//        checkOut을 조회할 때 연관관계 엔티티들의 fetch를 지연로딩으로 설정했기 때문에
//        join이 걸리지 않음
        Optional<CheckOut> foundId = checkOutDataRepository.findByBookId(1L);

//        해당 엔티티를 사용하면 select쿼리가 추가로 발생된다
        foundId.ifPresent(ch-> System.out.println(ch.getBook()));
    }
    @Test
    void join1(){
        CheckOut ch = em.createQuery("select ch from CheckOut ch join ch.book  where ch.id=1", CheckOut.class)
                .getSingleResult();
        System.out.println("ch = " + ch);

//        join을 직접 사용하여도 지연로딩을 설정했으므로 book엔티티는 프록시 객체로 설정된다.
//        즉, 사용시점에 다시 select쿼리를 날리는 N+1문제가 발생된다.
        System.out.println(ch.getBook());
    }
    @Test
    void join2(){
//        위 예제의 N+1 문제를 해결하기 위해서는 Fetch join을 사용하면 된다.
        CheckOut ch = em.createQuery("select ch from CheckOut ch join fetch     ch.book  where ch.id=1", CheckOut.class)
                .getSingleResult();
        System.out.println("ch = " + ch);

//        join을 직접 사용하여도 지연로딩을 설정했으므로 book엔티티는 프록시 객체로 설정된다.
//        즉, 사용시점에 다시 select쿼리를 날리는 N+1문제가 발생된다.
        System.out.println(ch.getBook());
    }
    @Test
    void findByJoinBook(){
        List<CheckOut> chList = checkOutDataRepository.findByJoinBook();
        System.out.println(chList.get(0).getBook());
    }
    @Test
    void findByFetchJoinBookAndUser(){
        List<CheckOut> chList = checkOutDataRepository.findByFetchJoinBookAndUser();
        System.out.println(chList.get(0).getUser());
        System.out.println(chList.get(0).getBook());
    }
    @Test
    void findByFetchJoinBookOn(){
        List<CheckOut> chList = checkOutDataRepository.findByFetchJoinBookOn();
    }
    @Test
    void joinTest1(){
        List<CheckOut> chList = checkOutDataRepository.joinTest1();
        System.out.println(chList.get(0).getBook());
    }
    @Test
    void findALl(){
        checkOutDataRepository.findAll();
    }
    @Test
    void findByCreatedDateGreaterThan(){
        checkOutDataRepository.findByCreatedDateGreaterThan(LocalDateTime.of(2000,1,1,11,11));
    }
    @Test
    void joinTest3(){
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        checkOutDataRepository.joinTest3(pageRequest);
    }
    @Test
    void sub1(){
        checkOutDataRepository.sub1();
    }
    @Test
    void sub2(){
        checkOutDataRepository.sub2();
    }
    @Test
    void sub3(){
        checkOutDataRepository.sub3();
    }
    @Test
    void task1(){
        checkOutDataRepository.task1();
    }
    @Test
    void task2(){
        List<CheckOut> checkOuts = checkOutDataRepository.task2();
        System.out.println("checkOuts = " + checkOuts);
    }




}








