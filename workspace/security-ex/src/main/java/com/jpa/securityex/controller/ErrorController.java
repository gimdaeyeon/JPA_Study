package com.jpa.securityex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/denied")
    public String denied(){
        return "error/denied";
    }

}
