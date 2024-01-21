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

//    게시글 리스트 페이징 처리
//    리스트 화면에 필요한 정보 : 번호, 제목, 조회수 날짜, 회원아이디
//    회원 로그인 아이디 Board에 없으므로 다음과 같은 방법을 고려함
//    1. 그냥 Board 조회 후 그래프 탐색
//     지연로딩을 사용하므로 Board 1개당 Member를 select하는 쿼리가 추가 발생(N+1)
//    2. fetch join으로 Board, Member 엔티티를 같이 조회 후 Service에서 DTO로 변환
//     Member의 불필요한 데이터를 가져오며, DTO로 변환하는 작업이 추가됨
//    3. jpql이나 queryDSL을 활용하여 원하는 데이터만 조회하고 DTO로 반환
//     필요한 데이터만 가져올 수 있으나 별도의 쿼리가 필요하고 화면에서 요구하는 데이터가 변경되면
//     해당 쿼리도 수정되어야 한다.

//    이 경우 여러 상황을 고려해야하며, 우리는 다음과 같이 판단함
//    1. 우리 어플리케이션의 서비스 중 게시판은 사용자들이 자주 이용하는 서비스이므로
//    불필요한 리소스 낭비를 줄이기 위해 필요한 데이터만 DTO에 담아 조회하는 것이 타당함
//    2. 게시판 목록을 조회하는 것은 readOnly
//    즉, 목록을 뽑아 수정, 삭제를 할 이유가 없으므로 DTO에 담았을 때 문제가 되지 않으며 오히려 안전함
//    결론 -> jpql, queryDSL로 DTO반환
//    연습을 위해 jpql로 처리
    @Override
    @Transactional(readOnly = true)
    public Page<BoardListDto> findBoardList(Pageable pageable) {
        return boardRepository.findListWithPage(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardEditDto findEditBoard(Long boardId) {
        Board foundBoard = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시물"));
        return BoardEditDto.from(foundBoard);
    }
}
















