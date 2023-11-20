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
        user.setAge(15);

        entityManager.persist(user);

        Board board = new Board();
        board.setContent("테스트내용");
        board.setTitle("테스트 제목");
        board.setUser(user);

        entityManager.persist(board);
    }

    @Test
    void find(){
        User user = entityManager.find(User.class,1L);

        user.boards.forEach(System.out::println);
    }

    @Test
    void update(){
        User user = entityManager.find(User.class,1L);
        user.setAge(20);

        user.boards.get(0).setTitle("수정");
    }

    @Test
    void delete(){
        User user = entityManager.find(User.class,1L);

        entityManager.remove(user.boards.get(0));
    }


}