package com.jpa.security00.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Spring security의 웹 보안 기능을 활성화 시킨다.
// 내부에 @Import가 있는데 이 어노테이션은 다른 @Configuration(설정 어노테이션)이 붙은 클래스 또는
// ImportSelector 인터페이스를 구현한 클래스를 Import해오기 위해 사용된댜.
// -> 설정 클래스에서 다른 설정 클래스를 불러오기 위해 사용한다.
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        HttpSecurity는 인증/인가설정을 하기 위한 객체이다.
        http
                .authorizeHttpRequests(//인가 정책 설정 메소드
                        authorize->
                                authorize.anyRequest() // 어떤 요청이든
                                        .authenticated() // 인증된 사용자만 접근 가능
                );

        http    //인증 방식 설정
                .formLogin(Customizer.withDefaults()); // form기반 로그인 방식
//        Customizer는 여러 보안설정이 가능한 객체
//        withDefaults() 는 기보 보안 설정을 사용
//        - 기본 보안 설정
//        1. /login 경로로 로그인 페이지 제공
//        2. username, password 파라미터 사용
//        3. 로그인 실패 시 /login?error로 리다이렉트
//        4. /logout 경로로 로그아웃 페이지 제공

        return http.build();
//        반환한 설정 객체를 바탕으로 SecurityFilterChain 객체를 빈등록하며
//        Spring Security 필터 체인으로 등록된다.
//          -> 쉽게 말하면 보안처리용 필터가 생성, 등록된다.

//        이 후 요청이 들어오면 요청이 컨트롤러에 도달하기 전에 필터 체인을 거치게 되므로 보안처리가 된다.
    }



}









