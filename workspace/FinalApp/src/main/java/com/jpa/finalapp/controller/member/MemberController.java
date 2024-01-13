package com.jpa.finalapp.controller.member;

import com.jpa.finalapp.domain.dto.member.MemberJoinDto;
import com.jpa.finalapp.domain.type.member.MemberGender;
import com.jpa.finalapp.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@Slf4j
@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/join")
    public String join(@ModelAttribute MemberJoinDto memberJoinDto, Model model){
//      values()는 열거형의 모든 상수를 배열형태로 반환한다. -> {NONE,MALE,FEMALE}
        MemberGender[] values = MemberGender.values();
        log.info("{}", Arrays.toString(values));
//        각 상수 객첼의 gender 필드를 getter를 통해 얻을 수 있다.
        log.info("{}", values[0].getGender());
//        enum에서 기본제공하는 name()으로 상수의 이름을 얻을 수 있다.
        log.info("{}",values[0].name());

        model.addAttribute("memberGenders",values);

        return "member/join";
    }

    @PostMapping("/join")
//    @Valid는 해당 파리미터의 유효성을 검증해준다.
//    무엇을 검증할지는 해당 객체(DTO) 유효성 검증 어노테이션을 통해 정의해준다
    public RedirectView join(@Valid MemberJoinDto memberJoinDto){
        log.info("memberJoinDto : {}",memberJoinDto);

        memberService.join(memberJoinDto);

        return new RedirectView("/");
    }


}











