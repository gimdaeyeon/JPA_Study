package com.jpa.filter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user/test")
    public void filterTest(){
        System.out.println("============핸들러 메소드 실행===============");
    }

}
