package com.jpa.security02.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
public class SecurityRestController {

    @GetMapping("/")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/my-login")
    public String myLogin(){
        return "로그인 페이지";
    }

    @GetMapping("/admin")
    public String adminMain(){
        return "admin";
    }

    @GetMapping("/board/list")
    public String boardList(){
        return "list";
    }

    @GetMapping("/context")
    public String context(HttpSession session){
//        SecurityContext를 꺼낼 수 있다. 내부의 인증객체도 꺼낼 수 있다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getAuthorities()); // 사용자 권한들
        System.out.println(authentication.isAuthenticated());// 인증 여부
        System.out.println(authentication.getPrincipal());// UserDetails를 구현한 User객체
        System.out.println(authentication.getCredentials()); // 비밀번호
        System.out.println(authentication.getName()); // 사용자 아이디

//        세션 내부에도 저장되어 있기 때문에 꺼내올 수 있다.
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        return "context";
    }

}













