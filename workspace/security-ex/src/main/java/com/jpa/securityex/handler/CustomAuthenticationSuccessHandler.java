package com.jpa.securityex.handler;

import com.jpa.securityex.provider.MemberDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//    커스텀 인증 성공 핸들러를 구현하는 방법은 여러가지가 있다.
//    1. AuthenticationSuccessHandler 인터페이스 구현
//    상세 설정을 직접 잡아줄 수 있다.
//    2. SimpleUrlAuthenticationSuccessHandler 클래스 확장
//    기본적인 기능을 편하게 구현가능 (인증 성공 후 로깅하고 특정 URL로 리다이렉트)
//    3. SavedRequestAwareAuthenticationSuccessHandler 클래스 확장
//    기존의 요청 정보를 저장하고 복원가능한 기능을 편하게 구현 가능(인증 성공 후 기존 요청으로 리다이렉트 가능)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

//        로깅처리
        log.info("로그인한 사용자 >> : {}", memberDetails.getLoginId());

//        login 성공 시 기본적으로 이동하 url 설정
        setDefaultTargetUrl("/board/list");

//        setAlwaysUseDefaultTargetUrl(true);

//        아래 코드가 있으면 사용자가 보낸 기존의 요청 URL로 redirection이 된다.
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
























