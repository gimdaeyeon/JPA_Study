package com.jpa.finalapp.repository.comment;

import com.jpa.finalapp.domain.entity.comment.Comment;
import com.jpa.finalapp.domain.entity.comment.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;

import static com.jpa.finalapp.domain.entity.comment.QComment.comment;

@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryDslRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Comment> commentListWithPage(Long boardId, Pageable pageable) {
//        컨텐츠 조회 쿼리
        List<Comment> contents = queryFactory.selectFrom(comment)
                .leftJoin(comment.member)
                .fetchJoin()
                .where(comment.board.id.eq(boardId))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset()) // 컨텐츠 시작번호 설정
                .limit(pageable.getPageSize())  // 몇 개씩 가져올지 설정
                .fetch();
//        카운트 조회 쿼리
        Long totalCount = queryFactory.select(comment.count())
                .from(comment)
                .where(comment.board.id.eq(boardId))
                .fetchOne();

        return new PageImpl<>(contents,pageable,totalCount);
    }

    @Override
    public Slice<Comment> commentListWithSlice(Long boardId, Pageable pageable) {

//        컨텐츠 조회 쿼리
        List<Comment> contents = queryFactory.selectFrom(comment)
                .leftJoin(comment.member)
                .fetchJoin()
                .where(comment.board.id.eq(boardId))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset()) // 컨텐츠 시작번호 설정
                .limit(pageable.getPageSize()+1)  // 항상 하나씩 더 가져온다.
                .fetch();

        boolean hasNext = false;

        if(contents.size()>pageable.getPageSize()){ // 실제 조회된 댓글 수 > 클라이언트가 요청한 댓글 수
            contents.remove(pageable.getPageSize()); // 마지막 댓글은 리스트에서 삭제
            hasNext = true; // 다음페이지 존재 여부를 저장
        }

        return new SliceImpl<>(contents,pageable,hasNext);
    }
}


























