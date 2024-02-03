package com.jpa.securityex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/board/list")
    public void boardList(){

    }

    @GetMapping("/board/detail")
    public void boardDetail(){

    }

    @GetMapping("/board/write")
    public void boardWrite(){

    }


}
