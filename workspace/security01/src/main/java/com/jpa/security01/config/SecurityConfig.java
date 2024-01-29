package com.jpa.security01.config;

import com.jpa.security01.handler.TempSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(//인가 정책 설정 메소드
                        authorize ->
                                authorize.anyRequest() // 어떤 요청이든
                                        .authenticated() // 인증된 사용자만 접근 가능
                );

        http
                .formLogin(form ->
                                form
//                                .loginPage("/my-login") // 사용자가 만든 로그인 url 진입
                                        .loginProcessingUrl("/my-login") // 로그인 처리 url
                                        .usernameParameter("loginId") // 사용자가 만든 로그인페이지의 아이디 파라미터명
                                        .passwordParameter("password")// 비밀번호 파리미터 명
                                        .defaultSuccessUrl("/") // 로그인 성공시 이동 url
//                                메소드 체이닝 방식으로 간단하게 설정해도 되지만 자세한 설정을 하고 싶다면
//                                핸들러를 별도로 만들어서 등록하면 된다.
                                        .successHandler(new TempSuccessHandler()) // 로그인 성공시 실행할 핸들러 등록
//                                .failureHandler()
                                        .failureUrl("/login") // 로그인 실패시 이동 url
                                        .permitAll() // 인증 여부와 상관없이 모든 클라이언트 접근을 허용
//                        위쪽의 인가 정책 설정 authorizeHttpRequests()에서 어떤 요청이든
//                        인증된 사용자만 접근 가능하도록 설정하였기 때문에 우리가 커스텀한 로그인 페이지도
//                        인증안하면 접근이 불가능하다.
//                        그러나 로그인 페이지를 인증해야 진입한다는것은 말이 안되므로 로그인 페이지느
//                        permitAll()로 인증 여부 상관없이 접근을 허용한다.
                );

        http
                .logout(logout -> logout
                                .logoutUrl("/logout") // 로그아웃 처리 url(로그아웃 처리는 기본적으로 post방식)
                                .logoutSuccessUrl("/my-login") // 로그아웃 성공 후 이동할 url
                                .deleteCookies("cookieName1", "cookieName2") // 로그아웃 후 삭제하고 싶은 쿠키이름들
//                                로그아웃 상세 설정을 위한 핸들러 등록
                                .addLogoutHandler((request, response, authentication) -> {
                                    request.getSession().invalidate();
                                })
//                                로그아웃 성공후 상세 설정을 위한 핸들러 등록
                                .logoutSuccessHandler((request, response, authentication) -> response
                                        .sendRedirect("/my-login"))
                );

        http
                .sessionManagement(session ->
                        session
                                .sessionFixation().changeSessionId() // 세션ID를 항상 고정하지 않고 로그인시 세션아이디를 교체한다.(Default)
                                .invalidSessionUrl("/invalidSession") // 세션이 유효하지 않으면 이동할 url
                                .maximumSessions(1) // 최대 세션 수
                                .maxSessionsPreventsLogin(true) // true: 현재 사용자의 인증 실패, false : 기존 사용자 세션 만료(default)
                                .expiredUrl("/expired") // 중복 로그인에 의하여 세션이 강제로 만료된 경우 이동할 url
                        );


        return http.build();
    }


}









