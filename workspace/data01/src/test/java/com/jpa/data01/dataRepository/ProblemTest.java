package com.jpa.data01.dataRepository;

import com.jpa.data01.domain.embedded.Address;
import com.jpa.data01.domain.entity.Book;
import com.jpa.data01.domain.entity.CheckOut;
import com.jpa.data01.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FlushModeType;
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
//        아래 쿼리들의 실행 순서를 확인해보자.
//        1. find를 한다.
        Optional<User> foundUser = userDataRepository.findById(1L);
//        2. set을 한다.
        foundUser.ifPresent(user->user.setName("수정했다!!"));
//        3. find를 한다.
        Optional<User> foundUser2 = userDataRepository.findById(2L);

//        결과는 select -> select -> update
//        set을 통해 update 쿼리가 실행되는 시점은 flush가 발생하는 시점이다.
//        즉, 메소드가 종료되어 commit되기 직전에 실행된다.
    }
    @Test
    void jpqlAndFlush2(){
//        아래 쿼리들의 실행 순서를 확인해보자.
//        1. find를 한다.
        Optional<User> foundUser = userDataRepository.findById(1L);
//        2. set
        foundUser.ifPresent(user->user.setName("수정했다!!"));
//        jpql로 select를 한다.
        User foundUser2 = em.createQuery("select u from User u where u.id =  2", User.class)
                .getSingleResult();
//        userDataRepository.findAll();

//        결과는 select -> update -> select

//        JPQL의 특징
//        엔티티 메니저의 find()를 실행시키는 경우 우선 영속성 컨텍스트에 조회하려는 id를 가진 엔티티가 있는지
//        먼저 검사한다. 없다면 DB에 쿼리를 날린다.
//        JPQL을 사용ㅇ하는 경우 쿼리를 생성하여 DB로 바로 날린다.
//        그런데 이때 문제가 생길 수 있다.
//        만약 직전 코드에서 데이터를 수정하기 위해 set을 사용했다면 DB에는 아직 반영되지 않은 상태일 것이다.
//        즉, 수정되기 전의 엔티티가 조회된다. 이렇게 되면 로직에 큰 문제가 생길 수 있으므로 JPQL로 쿼리를 날리기
//        전에  flush를 자동으로 실행하고 쿼리를 날린다.
//        그러면 변경감지를 통해 수정된 데이터가 모두 DB에 반영된 후 JPQL로 조회가 될 것이다.
    }
    @Test
    void jpqlAndFlush3(){
//        엔티티 메니저에는 플러쉬 모드를 설정할 수 있다.
//        FlushModeType.AUTO가 디폴트이며, 커밋 / jpql 사용 시 자동 플러쉬가 된다.
//        FlushModeType.COMMIT 은 커밋 시점에만 자동 플러쉬가 된다.
        em.setFlushMode(FlushModeType.COMMIT);

//        아래 쿼리들의 실행 순서를 확인해보자.
//        1. find를 한다.
        Optional<User> foundUser = userDataRepository.findById(1L);
//        2. set
        foundUser.ifPresent(user->user.setName("수정했다!!"));
//        jpql로 select를 한다.
        List<User> userList = em.createQuery("select u from User u where u.id <  10", User.class)
                .getResultList();
//        결과는 select -> select -> update

//        그렇다면 조회가 모두 끝난뒤 update가 이루어진 것인데
//        list에 담긴 1번 회원의 정보는 수정이 되었을까?
        System.out.println("userList = " + userList);

//        분명 update가 마지막에 실행되었는데 update이전에 list에 담긴 1번회원의 정보는 수정되어있다.
//        jpql은 쿼리를 DB로 바로 날리지만 조회결과를 가져왔을 때는 영속성 컨텍스트에 동일한 ID를 가진
//        엔티티가 존재하는지 검사한다.
//        만약 동일한 ID를 가진 엔티티가 존재한다면 어떻게 처리해야할까?

//        1. 두 엔티티를 모두 영속성 컨텍스트에 저장한다. -> 불가능하다. 같은 id를 가진 엔티티가 존재해서는 안된다.
//        2. 기존의 영속 컨텍스테 저장된 엔티티를 삭제하고 새로 가져온 엔티티를 등록한다.
//            가능한 방법이지만 그렇게되면 엔티티의 동일성 보장이 깨진다.
//        3. 기존 영속 컨텍스트에 저장된 엔티티를 유지하고 새로 조회한 엔티티를 버린다. -> 이 방식으로 작동한다.
//            엔티티가 영속 컨텍스트에 존재한다는 것은 이미 사용중인 엔티티라는 의미이다.
//              해당 엔티티와 다시 조회한 결과에서 동일성을 보장하기 위해서는 이방법을 사용해야한다.

        System.out.println("동일성을 보장하는가? "+(foundUser.get()==userList.get(0)));



    }










}



















