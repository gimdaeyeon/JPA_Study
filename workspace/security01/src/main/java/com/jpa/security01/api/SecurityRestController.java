package com.jpa.security01.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityRestController {

    @GetMapping("/")
    public String hello(){
        return "Hello!";
    }

    @GetMapping("/my-login")
    public String myLogin(){
        return "my-login";
    }
}
