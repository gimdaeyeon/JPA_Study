package com.jpa.basic3.domain.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional @Commit
class EntityTask {
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void save(){
        User user = new User();
        user.setLoginId("aaa");
        user.setPassword("1234");
        user.setAge(22);

        Board board = new Board();
        board.setContent("테스트내용");
        board.setTitle("테스트 제목");
        board.setUser(user);

        entityManager.persist(user);
        entityManager.persist(board);
    }

    @Test
    void find(){

//        Board foundBoard = entityManager.getReference(Board.class,1L);
//        Board foundBoard = entityManager.find(Board.class,1L);
        User user = entityManager.find(User.class,1L);

//        System.out.println("user = " + user);
//        user.boards.forEach(System.out::println);

        user.boards.forEach(System.out::println);
    }

    @Test
    void findLazy(){
        Board board = entityManager.find(Board.class,1L);
//        지연 로딩(Lazy)을 사용한 경우 Board만 조회하는 것을 볼 수 있다.
        User user = board.getUser();
//        user 객체를 가져왔으나 select가 날아가지 않는다.
//        현재 user에 담긴 객체는 프록시 객체이다. (가짜 객체)

        System.out.println(user.getAge());
    }

    @Test
    void findLazy2(){
        User user = entityManager.find(User.class,1L);
//        User를 조회하는 경우 Board는 join이 되지 않는다.
//        연관 관계에서 Board처럼 [다대일]이거나  [일대일] 인 경우
//        즉, 반대쪽이 [일]인 경우 즉시 로딩(Eager)을 default로 사용한다

//        그러나 User처럼 [일대다]이거나  [다대다]
//        즉, 반대쪽이 [다]인 경우 즉시 로딩(Lazy)을 default로 사용한다
        user.getBoards().get(0);

    }

    @Test
    void update(){
        Board board = entityManager.find(Board.class,1L);
        board.setTitle("수정");
    }

    @Test
    void delete(){
        User user = entityManager.find(User.class,1L);

        entityManager.remove(user.boards.get(0));
    }


}