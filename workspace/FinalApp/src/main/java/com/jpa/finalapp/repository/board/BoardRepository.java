package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>,BoardQueryDslRepository {
//    저장 처리(기본 제공 save())
//    게시물 상세보기(QueryDSL)
//    게시물 수정(변경 감지)
//    게시물 번호로 삭제(기본제공 deleteById() 사용)
//    게시물 리스트 페이징 처리
}
