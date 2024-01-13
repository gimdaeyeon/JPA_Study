package com.jpa.finalapp.repository.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;

import java.util.Optional;

public interface BoardQueryDslRepository {
    Optional<BoardDetailDto> findBoardDetailById(Long boardId);
}
