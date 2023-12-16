package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.embedded.Address;
import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.entity.CheckOut;
import com.jpa.data01.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional @Commit
public class ProblemTest {

    @Autowired BookDataRepository bookDataRepository;
    @Autowired BookQueryRepository bookQueryRepository;
    @Autowired UserQueryRepository userQueryRepository;
    @Autowired UserDataRepository userDataRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void oneToOne(){
//        bookDataRepository.findAll();
//        bookDataRepository.findByName("Rank");

//        위의 쿼리 결과를 확인해보면, 조회된 엔티티의 ID를 통해 CheckOut을 조회하는 쿼리가 만들어지는 것을
//        볼 수 있다. Book엔티티의  CheckOut필드를 지연로딩으로 처리하였지만 자동으로 select쿼리가 생성된다.

//        em.find(Book.class,1L);

//        이것은 OneToOne 양방향 관계에서 발생하는 N+1문제이다.
//        checkOut은 정상적으로 지연로딩이 적용되고 있다.
        em.find(CheckOut.class,1L);

//      이유가 뭘까?
//      1. 테이블 관계                       2. 객체 관계
//      JPA_BOOK       JPA_CHECK_OUT       BOOK       CheckOut
//      ID              id                  id          id
//      NAME            BOOK_ID(fk)         name        book(*)
//      PHONE           USER_ID(fk)         phone       user(*)
//      BIRTH           deleted             birth       deleted
//      ADDRESS                             address
//      DELETED                             deleted
//                                          checkOut(*)

//        위의 관계에서 JPA_CHECK_OUT은 BOOK_ID를 fk칼럼으로 가지고 있다.
//        즉, JPA_CHECK_OUT를 조회할 때 연관 관계가 없다면 checkOUt엔티티의 book필드에 null이 저장되고
//        연관관계가 존재한다면 book필드에 프록시 객체가 들어간다.(지연로딩)

//        그런데 반대로 JPA_BOOK테이블은 fk를 가지고 있지 않다.
//        즉, JPA_BOOK 테이블만 봐서는 Book엔티티의 checkOut필드에 null이 들어가야 할 지 프록시 객체가 들어갈지
//        알 수가 없다. 그러므로 연관곤계 유무를 알기 위해서는 JPA_CHECK_OUT을 조회할 수 밖에 없다.

//        정리하자면 OneToOne의 양방향 관계에서 주인이 아닌 엔티티로 조회를 하면 패러다임 불일치로 인해
//        N+1이 발생한다.

//        어떻게 해결해야할까?
//        명쾌한 해답은 없다.
//        1. 꼭 필요한게 아니라면 OneToOne은 단방향으로 사용한다.
//        2. OneToMany ManyToOne관계로 변경한다.(OneToOne이라는 논리적인 관계를 포기해야한다.)
//        3. 직접적인 연관관계를 끊고 서로를 확인할 수 있는 key를 칼럼으로 추가하여 직접 join걸어 사용
//        (jpa의 연관관계 그래프탐색 포기)
//        4. 어쩔 수 없으니 fetch 조인을 사용해서 쿼리 수라도 줄여본다.
    }
    @Test
    void embedded(){
//        쿼리 메소드의 반환 타입을 Embedded로 설정 가능하다.
        List<Address> addressList = userQueryRepository.findUsersAddress();

//        임베디드 타입은 엔티티와는 다르게 변경감지가 일어나지 않는다.
//        엔티티 타입만 영속화가 가능하기 때문이다.
        addressList.get(0).setAddress("수정!!!");

//        변경감지를 통해 수정하고 싶다면 엔티티를 가져와야한다.
        Address address = new Address("수정","한다","12345");
        Optional<User> foundUser = userDataRepository.findById(1L);
        foundUser.ifPresent(user->user.setAddress(address));
    }
    @Test
    void jpqlAndFlush(){

    }







}



















