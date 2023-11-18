package com.jpa.basic2.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
@Slf4j
class EntityTest {
    @PersistenceContext
    EntityManager entityManager;

    @Test @DisplayName("양방향 참조")
    void save2(){
        User user = new User();
        user.setLoginId("aaa");
        user.setPassword("1234");
        user.setName("홍길동");
        user.setAge(22);

        Board board1 = new Board();
        board1.setTitle("ㅎㅇ");
        board1.setContent("방가뵹");

        Board board2 = new Board();
        board2.setTitle("ㅎㅇ2");
        board2.setContent("방가뵹2");

        user.getBoard().add(board1);
        user.getBoard().add(board2);

        entityManager.persist(user);
        entityManager.persist(board1);
        entityManager.persist(board2);
    }

    @Test
    void save(){
        User user = new User();
        user.setLoginId("aaa");
        user.setPassword("1234");
        user.setName("홍길동");
        user.setAge(22);

        Board board1 = new Board();
        board1.setTitle("ㅎㅇ");
        board1.setContent("방가뵹");

        board1.setUser(user);

        Board board2 = new Board();
        board2.setTitle("ㅎㅇ2");
        board2.setContent("방가뵹2");

        board2.setUser(user);

        entityManager.persist(user);
        entityManager.persist(board1);
        entityManager.persist(board2);
    }
//
//    @Test
//    void find(){
//        Board board = entityManager.find(Board.class,2L);
//
//        User user = board.getUser(); // 객체 그래프 탐색
//        System.out.println("user = " + user);
//
//    }
//
//    @Test
//    void find2(){
//        List<Board> boardList = entityManager
//                .createQuery(
//                        "select b from Board b join b.user u where u.name = :name",
//                        Board.class)
//                .setParameter("name","홍길동")
//                .getResultList();
//
//        boardList.forEach(System.out::println);
//    }
//
//    @Test
//    void update(){
//        User user = new User();
//        user.setLoginId("bbb");
//        user.setPassword("1234");
//        user.setName("이유리");
//        user.setAge(11);
//
////        새로운 회원 정보 등록
//        entityManager.persist(user);
//
//        // 2번 게시물 조회
//        Board board = entityManager.find(Board.class,2L);
//        board.setUser(user);
//    }
//    @Test
//    void remove(){
//        User user = entityManager.find(User.class, 1L);
//
//        String query = "select b from Board b where b.user.id =:userId";
//        Board board = entityManager.createQuery(query, Board.class)
//                .setParameter("userId",user.getId())
//                .getSingleResult();
//        board.setUser(null);    // 연관관계 해제
//
//        entityManager.remove(user);
//    }



}













