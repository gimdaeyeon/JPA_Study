package com.jpa.finalapp.controller.member;

import com.jpa.finalapp.domain.dto.member.MemberJoinDto;
import com.jpa.finalapp.domain.dto.member.MemberLoginDto;
import com.jpa.finalapp.domain.type.member.MemberGender;
import com.jpa.finalapp.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String login(@ModelAttribute MemberLoginDto memberLoginDto){

        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Valid MemberLoginDto memberLoginDto,
                      BindingResult result,
                      HttpSession session){
        log.info("memberLoginDto : {}",memberLoginDto);

        if(result.hasErrors()){
            return "member/login";
        }
        Long memberId = memberService.login(memberLoginDto.getLoginId(), memberLoginDto.getPassword());

        session.setAttribute("memberId",memberId);

        return "redirect:/";
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
    public String join(@Valid MemberJoinDto memberJoinDto,
                             BindingResult bindingResult, Model model){
        log.info("memberJoinDto : {}",memberJoinDto);

//        BindingResult는 검증 오류를 보관하는 객체이다.
//        @Valid로 검증한 DTO에서 발생하는 오류가 있다면 모두 BindingResult 객체에 저장된다.
//        주의사항 : 매개변수의 순서가 중요하다. 검증 대상 직후에 선언해야만 한다.
//        중간에 다른 매개변수가 있으면 안된다.
//        BindingResult를 통해 유효성 검증 통과 여부를 알 수 있다.
//        만약 검증 대상에서 한 번이라도 오류가 발생되면 hasErrors()는 true를 반환한다.
        if (bindingResult.hasErrors()){
            model.addAttribute("memberGender",MemberGender.values());
            return "member/join";
        }

        memberService.join(memberJoinDto);

        return "redirect:/";
    }


}











