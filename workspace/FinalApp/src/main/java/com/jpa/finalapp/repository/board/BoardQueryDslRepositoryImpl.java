package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.jpa.finalapp.domain.entity.board.QBoard;
import com.jpa.finalapp.domain.entity.comment.QComment;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.jpa.finalapp.domain.entity.board.QBoard.board;

@RequiredArgsConstructor
public class BoardQueryDslRepositoryImpl implements BoardQueryDslRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<BoardDetailDto> findBoardDetailById(Long boardId) {
        QBoard subBoard = new QBoard("subBoard");
        QComment subComment = new QComment("subComment");

        JPQLQuery<Long> boardCountSubQuery = JPAExpressions.select(subBoard.count())
                .from(subBoard)
                .where(board.member.eq(subBoard.member));
        JPQLQuery<Long> commentCountSubCount = JPAExpressions.select(subComment.count())
                .from(subComment)
                .where(board.member.eq(subComment.member));

        return Optional.ofNullable(queryFactory.select(
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
                .where(board.id.eq(boardId))
                .fetchOne());
    }
}
