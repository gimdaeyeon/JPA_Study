package com.jpa.finalapp.repository.comment;

import com.jpa.finalapp.domain.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>,CommentQueryDslRepository {
//    작성
//    수정
//    삭제
//    리스트 조회 페이징
}
