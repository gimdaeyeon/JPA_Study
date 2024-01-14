package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.embedded.member.MemberAddress;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.board.QBoard;
import com.jpa.finalapp.domain.entity.comment.QComment;
import com.jpa.finalapp.domain.entity.member.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jpa.finalapp.domain.entity.board.QBoard.board;

@SpringBootTest
@Transactional
@Commit
class BoardRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;
    @Autowired
    BoardRepository boardRepository;
    @PersistenceContext
    EntityManager em;

    Board boardEntity;
    Member memberEntity;
    @BeforeEach
    void setUp() {
        memberEntity = Member.builder()
                .loginId("test")
                .password("1234")
                .memberAddress(new MemberAddress("강남구", "논현동", "12345"))
                .birth(LocalDate.of(2000, 10, 2))
                .name("김철수")
                .phoneNumber("01012341234")
                .build();

        em.persist(memberEntity);

        boardEntity = Board.builder()
                .title("test")
                .content("test")
                .member(memberEntity)
                .build();

    }

    @Test
    void detail() {
//        게시물 id, title, content, viewCount, createdDate
//        회원 id, loginId
//        추가 게시물 작성자가 작성한 총 게시물 수, 총 리플 수

//        특정 게시물 번호로 조회를 해야한다. (단건조회)
        QBoard subBoard = new QBoard("subBoard");
        QComment subComment = new QComment("subComment");

        JPQLQuery<Long> boardCountSubQuery = JPAExpressions.select(subBoard.count())
                .from(subBoard)
                .where(board.member.eq(subBoard.member));
        JPQLQuery<Long> commentCountSubCount = JPAExpressions.select(subComment.count())
                .from(subComment)
                .where(board.member.eq(subComment.member));

        BoardDetailDto boardDetailDto = queryFactory.select(
                        Projections.constructor(BoardDetailDto.class,
                                board.id,
                                board.title,
                                board.content,
                                board.member.loginId,
                                board.createdDate.stringValue(),
                                board.viewCount,
                                board.member.id,
                                boardCountSubQuery,
                                commentCountSubCount)
                )
                .from(board)
                .where(board.id.eq(1L))
                .fetchOne();
    }

    @Test
    @DisplayName("상세보기")
    void selectDetail(){
        //given
        Board savedBoard = boardRepository.save(boardEntity);
        //when
        Optional<BoardDetailDto> boardOpt = boardRepository.findBoardDetailById(savedBoard.getId());
        //then
        assertThat(boardOpt.orElse(null))
                .extracting(BoardDetailDto::getBoardId)
                .isEqualTo(savedBoard.getId());
        System.out.println("boardOpt = " + boardOpt.orElse(null));
    }
    
    @Test
    @DisplayName("게시글 수정")
    void updateBoard(){
        // given
        Board savedBoard = boardRepository.save(boardEntity);
        // when
        savedBoard.modifyBoardDetail("제목 수정","내용 수정");
        savedBoard.increaseViewCount();
        boardRepository.flush();
        em.clear();

        // then
        Board foundBoard = boardRepository.findById(savedBoard.getId()).orElse(null);

        assertThat(foundBoard)
                .extracting(Board::getTitle,Board::getContent,Board::getViewCount)
                .containsExactly("제목 수정", "내용 수정",1);
    }
    @Test
    @DisplayName("삭제하기")
    void boardDelete(){
        // given
        Board savedBoard = boardRepository.save(boardEntity);
        boardRepository.flush();
        em.clear();
        // when
        boardRepository.deleteById(savedBoard.getId());
        // then
        assertThat(boardRepository.findById(savedBoard.getId()).orElse(null))
                .isNull();

    }
    @Test
    @DisplayName("게시물 리스트 조회")
    void findListWithPage(){
        // given
        List<Board> boards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            boards.add(
                    Board.builder()
                            .title("test" +i)
                            .content("test" +i)
                            .member(memberEntity)
                            .build()
            );
        }

        boardRepository.saveAll(boards);
        boardRepository.flush();
        em.clear();

        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(0, 10, sortById);

        // when
        Page<BoardListDto> boardPages = boardRepository.findListWithPage(pageRequest);

        // then

        assertThat(boardPages.getTotalElements()).isEqualTo(100);
        boardPages.getContent().forEach(System.out::println);


    }


    
    
    


}









