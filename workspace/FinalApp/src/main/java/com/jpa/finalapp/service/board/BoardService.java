package com.jpa.finalapp.service.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.jpa.finalapp.domain.dto.board.BoardEditDto;
import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.dto.board.BoardWriteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {
    void registerBoard(BoardWriteDto boardWriteDto, Long memberId);
    void modifyBoard(BoardEditDto boardEditDto);
    void removeBoard(Long boardId);
    BoardDetailDto findBoardDetail(Long boardId);
    Page<BoardListDto> findBoardList(Pageable pageable);
    BoardEditDto findEditBoard(Long boardId);
}
