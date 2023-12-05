package com.jpa.task.controller;

import com.jpa.task.domain.entity.Board;
import com.jpa.task.service.BoardService;
import com.jpa.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequestMapping("/board/*")
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;


    @GetMapping("/list/{page}")
    public String showList(@PathVariable("page") int page, Model model) {
        List<Board> boardList = boardService.findAllWithPagination(page, 10);
        model.addAttribute("boardList", boardList);
        return "board/boardList";
    }

    @GetMapping("/write")
    public void write(){}
    @PostMapping("/write")
    public RedirectView write(Board board, @SessionAttribute("userId")Long userId){
        board.setUser(userService.findUser(userId));
        boardService.boardWrite(board);
        return new RedirectView("/board/list/1");
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id")Long id, Model model){
        model.addAttribute("board",boardService.detail(id));
        return "board/detail";
    }

}
























