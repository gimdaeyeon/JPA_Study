package com.jpa.securityex.controller;

import com.jpa.securityex.domain.dto.MemberJoinDto;
import com.jpa.securityex.domain.dto.MemberLoginDto;
import com.jpa.securityex.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/*")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) boolean error,
                        @RequestParam(value = "message",required = false) String message){
        return "member/login";
    }

    @PostMapping("/login")
    public void login(MemberLoginDto loginDto){
        memberService.login(loginDto.getLoginId(),loginDto.getPassword());
    }

    @GetMapping("/join")
    public String join(){
        return "member/join";
    }

    @PostMapping("/join")
    public RedirectView join(MemberJoinDto memberJoinDto){
        String password = memberJoinDto.getPassword();
        memberJoinDto.setPassword(passwordEncoder.encode(password));
        memberService.saveMember(memberJoinDto);
        return new RedirectView("/member/login");
    }


}
