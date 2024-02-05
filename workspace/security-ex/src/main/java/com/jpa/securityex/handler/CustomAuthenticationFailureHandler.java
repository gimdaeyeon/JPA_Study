package com.jpa.securityex.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {
//    인증 실패 핸들러를 만드는 여러 방법이 있다.
//    1. AuthenticationFailureHandler 인터페이스르 구현
//    상세 설정
//    2. SimpleUrlAuthenticationFailureHandler 클래스 구현
//    기본적인 기능을 편하게 구현가능 (인증 실패 후 로깅하고 특정 URL로 리다이렉트)
//    3. ExceptionMappingAuthenticationFailureHandler 
//    발생되는 예외 타입에 따라 다른 URL로 이동하는 것을 쉽게 구현 가능
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        실패시 기본 url
        setDefaultFailureUrl("/member/login?error=true");

//        각 예외별 이동할 URL을 저장하는 Map
        Map<String,String> failureUrlMap = new HashMap<>();

//        존재하지 않는 회원 아이디면 발생
        failureUrlMap.put(UsernameNotFoundException.class.getName(),"/member/login?error=true&message=not_found");
//        비밀번호가 일치하지 않으면 발생
        failureUrlMap.put(BadCredentialsException.class.getName(),"/member/login?error=true&message=bad_credentials");

//        예외별 URL매핑을 설정하는 메서드
        setExceptionMappings(failureUrlMap);

        log.error("인증 실패 >> {}",exception.getMessage());

        super.onAuthenticationFailure(request, response, exception);
    }
}



















