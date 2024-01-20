package com.jpa.finalapp.controller.board;

import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.dto.board.BoardWriteDto;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.service.board.BoardService;
import com.jpa.finalapp.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String boardList(
//            page, size, sort 파라미터가 넘어오지 않을 때 Pageable의 디폴트 설정을
//            어노테이션으로 쉽게 할 수 있다.
            @PageableDefault(page = 1,
             size = 10,sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable
            , Model model){
//        Spring Data JPA가 Pageable 인터페이스를 제공하며 페이징 처리와 정렬기능을 간편하게
//        구현하도록 도와준다.
//        핸들러 메소드의 매개변수로도 사용이 가능하며, page와 size를 바인딩할 수 있다.
//        바인딩 예시 :/board/list/?page=1&size=10&sort=id,desc
        Page<BoardListDto> boardPage = boardService.findBoardList(pageable);

//        Page의 주요 메소드
        log.info("content list : {}", boardPage.getContent());
        log.info("total count : {}", boardPage.getTotalElements());
        log.info("total pages : {}", boardPage.getTotalPages());
        log.info("hasNext : {}", boardPage.hasNext());
        log.info("hasPrev : {}", boardPage.hasPrevious());
        log.info("size : {}", boardPage.getSize());
        log.info("sort : {}", boardPage.getSort());

        model.addAttribute("boardPages", boardPage);

        return "board/list";
    }
    @GetMapping("/detail")
    public String boardDetail(){
        return "board/detail";
    }
    @GetMapping("/write")
    public String boardWrite(@ModelAttribute BoardWriteDto boardWriteDto,
                             @SessionAttribute(name = "memberId", required = false) Long memberId,
                             Model model,RedirectAttributes redirectAttributes){

        if(memberId == null){
            redirectAttributes.addFlashAttribute("errorMessage","로그인이 필요한 서비스입니다");
            return "redirect:/board/list";
        }

        String loginId = memberService.findLoginId(memberId);
        model.addAttribute("loginId",loginId);

        return "board/write";
    }
    @PostMapping("/write")
    public String boardWrite(@Valid BoardWriteDto boardWriteDto,
                             BindingResult result,
                             @ModelAttribute("loginId") @RequestParam("loginId") String loginId,
                             @SessionAttribute(required = false) Long memberId,
                             Model model){

        if (result.hasErrors()){
            model.addAttribute("errorMessage","제목과 내용은 필수 입력사항입니다");
            return "board/write";
        }

        boardService.registerBoard(boardWriteDto,memberId);
        return "redirect:/board/list";
    }




}
