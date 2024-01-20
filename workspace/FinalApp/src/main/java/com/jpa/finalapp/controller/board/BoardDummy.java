package com.jpa.finalapp.controller.board;

import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.repository.board.BoardRepository;
import com.jpa.finalapp.repository.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardDummy {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

//    @PostConstruct
    public void createDummy(){
        Long memberId = memberRepository.findIdByLoginIdAndPassword("aaa", "1234")
                .orElse(null);
        Member member = memberRepository.findById(memberId).get();

        List<Board> list = new ArrayList<>();

        for (int i = 0; i < 77; i++) {
            Board board = Board.builder()
                    .title("test title" + i)
                    .content("test content" + i)
                    .member(member)
                    .build();

            list.add(board);
        }

        boardRepository.saveAll(list);

    }


}









