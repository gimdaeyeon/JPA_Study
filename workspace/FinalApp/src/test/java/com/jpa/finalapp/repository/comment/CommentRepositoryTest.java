package com.jpa.finalapp.repository.comment;

import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.comment.Comment;
import com.jpa.finalapp.domain.entity.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional @Commit
class CommentRepositoryTest {
    @Autowired CommentRepository commentRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .loginId("test")
                .password("1234")
                .memberAddress(new MemberAddress("강남구", "논현동", "12345"))
                .birth(LocalDate.of(2000, 10, 2))
                .name("김철수")
                .phoneNumber("01012341234")
                .build();

        em.persist(member);

        Board board = Board.builder()
                .title("test")
                .content("test")
                .member(member)
                .build();

        em.persist(board);

        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < 34; i++) {
            commentList.add(
                    Comment.builder()
                            .content("test")
                            .board(board)
                            .member(member)
                            .build()
            );
        }
        commentRepository.saveAllAndFlush(commentList);
        em.clear();
    }

    @Test
    void selectAll(){
//        잘 들어가는 지 확인
        List<Comment> comments = commentRepository.findAll();
        System.out.println("comments = " + comments);
    }

    @Test
    void commentListWithPage(){
        PageRequest pageInfo = PageRequest.of(0,10);
        Page<Comment> comments = commentRepository.commentListWithPage(1L, pageInfo);

        comments.getContent().forEach(System.out::println);

    }

    @Test
    void commentListWithSliceTest(){
        PageRequest pageInfo = PageRequest.of(0,10);
        Slice<Comment> comments = commentRepository.commentListWithSlice(1L, pageInfo);

        comments.getContent().forEach(System.out::println);
        System.out.println(comments.hasNext());
    }


















}
