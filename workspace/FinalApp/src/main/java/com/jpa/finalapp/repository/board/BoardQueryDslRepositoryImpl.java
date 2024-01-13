package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class BoardQueryDslRepositoryImpl implements BoardQueryDslRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<BoardDetailDto> findBoardDetailById(Long boardId) {
        return Optional.empty();
    }
}
