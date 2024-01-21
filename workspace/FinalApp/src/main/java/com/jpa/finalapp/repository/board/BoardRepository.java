package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>,BoardQueryDslRepository {
//    저장 처리(기본 제공 save())
//    게시물 상세보기(QueryDSL)
//    게시물 수정(변경 감지)
//    게시물 번호로 삭제(기본제공 deleteById() 사용)
//    게시물 리스트 페이징 처리
//      리스트 화면에 필요한 정보 : 게시물 번호, 제목, 조회수, 날짜, 회원 아이디
//      회원 id는 Board에 없으므로 다음과 같은 방법을 고려함
//      1. 그냥 Board를 조회 후 그래프 탐색
//       지연로딩을 사용하므로 Board 1개당 Member를 select하는 쿼리가 추가 발생함(N+1)
//      2. fetch join으로 Board, Member 엔티티를 같이 조회 후 service에서 Dto로 변환
//        (select b from Board b join fetch b.member)
//        Member의 불필요한 데이터를 가져오며, Dto변환 작업 추가됨
//      3. jpql이나 QueryDsl을 활용하여 원하는 데이터만 조회하고 Dto로 반환
//        필요한 데이터만 가져올 수 있으나 별도의 쿼리가 필요하고 화면에서 요구하는
//        데이터가 변경되면 해당 쿼리도 수정되어야함.

//    이 경우 여러 상황을 고려해야하며, 우리는 다음과 같이 판단함.
//    1. 우리 어플리케이션의 서비스 중 게시판은 사용자들이 자주 이용하는 서비스이므로
//    불필요한 리소스 낭비를 줄이기 위해 필요한 데이터만 DTO에 담아 조회하는 것이 타당할 수 있다.
//    2. 게시판 목록을 조회하는 것은 readOnly
//    즉, 목록을 뽑아 수정, 삭제를 할 이유가 없으므로 DTO에 담았을 때 문제가 되지 않으며 오히려 안전함.
//    결론 -> jpql, queryDSL로 DTO 반환
//    연습을 위해 jpql사용


//    프로젝션이 엔티티가 아니라 Dto인 경우 fetch join이 불가능하다.
//    fetch join은 연관된 여러 엔티티를 즉시 로딩, 영속화시켜 N+1문제를 해결한다.
//    그런데 프로젝션이 엔티티가 아닌 엔티티의 특정 필드나, 특정 필드를 사용하는 DTO인 경우에는
//    select의 결과를 영속성 컨텍스트와 무관하게 바로 가져오게 되므로 fetch join이 성립되지 않는다.
    @Query("""
                select new com.jpa.finalapp.domain.dto.board.BoardListDto(
                   b.id, b.title, b.viewCount,b.createdDate, m.loginId
                )    
                from Board b left join b.member m
            """)
    Page<BoardListDto> findListWithPage(Pageable pageable);

//    게시물 수정을 위한 조회
    @Query("select b from Board b join fetch b.member where b.id = :boardId")
    Optional<Board> findBoardById(@Param("boardId")Long boardId);


}









