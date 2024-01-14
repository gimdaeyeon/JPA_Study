package com.jpa.finalapp.service.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.jpa.finalapp.domain.dto.board.BoardEditDto;
import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.dto.board.BoardWriteDto;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.repository.board.BoardRepository;
import com.jpa.finalapp.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void registerBoard(BoardWriteDto boardWriteDto, Long memberId) {
        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원"));

        Board board = boardWriteDto.toEntity();
        board.setAuthor(author);
        boardRepository.save(board);
    }

    @Override
    public void modifyBoard(BoardEditDto boardEditDto) {
        Board board = boardRepository.findById(boardEditDto.getBoardId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물"));

        board.modifyBoardDetail(boardEditDto.getTitle(),boardEditDto.getContent());
    }

    @Override
    public void removeBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Override
    public BoardDetailDto findBoardDetail(Long boardId) {
//        상세보기 페이지에 진입 할 때 게시물의 조회수도 증가시켜야 한다.
//        조회수 증가는 변경감지를 이용할 것이므로 엔티티를 먼저 조회한다.
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물"));

        board.increaseViewCount();

//        findBoardDetailById()은 jpql을 생성하여 조회하므로 먼저 flush가 일어난다.
//        즉, 위에 증가시킨 조회수가 DB에 반영된 후 조회를 한다.
        return boardRepository.findBoardDetailById(boardId)
                .orElseThrow(()->new IllegalStateException("존재하지 않는 게시물"));
    }

    @Override
    public Page<BoardListDto> findBoardList(Pageable pageable) {
        return null;
    }
}
