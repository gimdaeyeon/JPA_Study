package com.jpa.security00.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityRestController {

    @GetMapping("/")
    public String hello(){
        return "Hello!";
    }
}
