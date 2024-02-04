package com.jpa.securityex.controller;

import com.jpa.securityex.provider.MemberDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/board/list")
    public String boardList(HttpSession session){
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        Authentication authentication = securityContext.getAuthentication();
        System.out.println("authentication.getName() = " + authentication.getName());
        System.out.println("authentication = " + authentication.getCredentials());
        System.out.println("authentication = " + authentication.getAuthorities());

//        인증이 끝나면 getPrincipal() 은 UserDetails 타입의 객체를 반환한다.
//        그런데 우리는 UserDetails를 구현하여 커스텀했으므로 우리가 만든 객체가 반환될 것이다.

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        System.out.println("memberDetails = " + memberDetails);
        return "board/list";
    }

    @GetMapping("/board/detail")
    public void boardDetail(@AuthenticationPrincipal MemberDetails memberDetails){
        System.out.println("memberDetails = " + memberDetails);
    }

    @GetMapping("/board/write")
    public void boardWrite(){

    }


}
