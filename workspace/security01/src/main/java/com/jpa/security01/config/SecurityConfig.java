package com.jpa.security01.config;

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
                                .failureUrl("/login") // 로그인 실패시 이동 url
                                .permitAll() // 인증 여부와 상관없이 모든 클라이언트 접근을 허용
                );


        return http.build();
    }


}









