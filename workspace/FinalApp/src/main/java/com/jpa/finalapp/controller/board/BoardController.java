package com.jpa.finalapp.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/*")
public class BoardController {

    @GetMapping("/list")
    public String boardList(){
        return "board/list";
    }
    @GetMapping("/detail")
    public String boardDetail(){
        return "board/detail";
    }
    @GetMapping("/write")
    public String boardWrite(){
        return "board/write";
    }




}
