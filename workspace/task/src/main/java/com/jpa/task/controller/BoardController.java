package com.jpa.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/board/*")
@Controller
public class BoardController {
    @GetMapping("/list")
    public String showList(){
        return "board/boardList";
    }


}
