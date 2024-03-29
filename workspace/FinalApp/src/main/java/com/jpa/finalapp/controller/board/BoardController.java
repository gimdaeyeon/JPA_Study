package com.jpa.finalapp.controller.board;

import com.jpa.finalapp.domain.dto.board.BoardDetailDto;
import com.jpa.finalapp.domain.dto.board.BoardEditDto;
import com.jpa.finalapp.domain.dto.board.BoardListDto;
import com.jpa.finalapp.domain.dto.board.BoardWriteDto;
import com.jpa.finalapp.domain.dto.common.PageBlock;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.service.board.BoardService;
import com.jpa.finalapp.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        int pageNumber = pageable.getPageNumber()-1;
        int pageSize = pageable.getPageSize();
        Sort sort = pageable.getSort();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

//        Spring Data JPA가 Pageable 인터페이스를 제공하며 페이징 처리와 정렬기능을 간편하게
//        구현하도록 도와준다.
//        핸들러 메소드의 매개변수로도 사용이 가능하며, page와 size를 바인딩할 수 있다.
//        바인딩 예시 :/board/list/?page=1&size=10&sort=id,desc
        Page<BoardListDto> boardPage = boardService.findBoardList(pageRequest);

        PageBlock<BoardListDto> pageBlock = new PageBlock<>(5, pageRequest, boardPage);

//        Page의 주요 메소드
        log.info("content list : {}", boardPage.getContent());
        log.info("total count : {}", boardPage.getTotalElements());
        log.info("total pages : {}", boardPage.getTotalPages());
        log.info("hasNext : {}", boardPage.hasNext());
        log.info("hasPrev : {}", boardPage.hasPrevious());
        log.info("size : {}", boardPage.getSize());
        log.info("sort : {}", boardPage.getSort());

        model.addAttribute("boardPages", boardPage);
        model.addAttribute("pageBlock", pageBlock);
        return "board/list";
    }
    @GetMapping("/detail/{boardId}")
    public String boardDetail( @PathVariable("boardId") Long boardId,Model model){

        BoardDetailDto boardDetail = boardService.findBoardDetail(boardId);
        model.addAttribute("board",boardDetail);

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

    @GetMapping("/modify/{boardId}")
    public String boardModify(@PathVariable("boardId") Long boardId,Model model){
        model.addAttribute("board",boardService.findEditBoard(boardId));
        return "board/edit";
    }
    @PostMapping("/modify/{boardId}")
    public String boardModify(@PathVariable("boardId")Long boardId,
                            @Valid @ModelAttribute("board") BoardEditDto boardEditDto,
                                    BindingResult result, Model model){
        boardEditDto.setBoardId(boardId);
        if(result.hasErrors()){
            model.addAttribute("errorMessage","제목과 내용은 필수 입력사항 입니다");
            return "board/edit";
        }
        boardService.modifyBoard(boardEditDto);

        return "redirect:/board/detail/"+boardId;
    }
    @GetMapping("/remove/{boardId}")
    public RedirectView boardDelete(@PathVariable("boardId")Long boardId){
        boardService.removeBoard(boardId);
        return new RedirectView("/board/list");
    }



}
