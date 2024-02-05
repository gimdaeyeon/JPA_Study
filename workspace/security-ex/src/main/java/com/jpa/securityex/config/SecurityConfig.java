package com.jpa.securityex.config;

import com.jpa.securityex.domain.type.Role;
import com.jpa.securityex.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
//        기본(Bcrypt)인코딩 방식을 사요한다.
//        암호화를 하면 `{인코딩방식}암호화된 비밀번호` 형식으로 변환된다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

//        암호화하지 않고 평문을 그대로 사용하고 싶으면 아래 코드를 사용한다.
//        return NoOpPasswordEncoder.getInstance();
    }

    //    Spring Security는 모든 자원에대해 보안처리를 한다.
//    그런데 정적 자원은 인증, 인가가 불필요한 경우가 많다.
//    만약 정적 자원(이미지 등) 에 대해 특별한 절차가 필요없다면, WebSecurityCustomizer를
//    활용하여 처리할 수 있다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//        WebSecurityCustomizer는 함수형 인터페이스이다.
//        리턴에서 람다식 바로 사용해도 된다.
        return web ->
                web.ignoring()
                        .requestMatchers(
                                PathRequest.toStaticResources()
                                        .atCommonLocations() // 정적리소스 css,js, 이미지 등이 들어있는  /static 경로
                        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/member/join", "member/login").permitAll()
                                .requestMatchers("/board/list", "/board/detail").hasRole(Role.MEMBER.name())
                                .requestMatchers("/board/write").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )


                .formLogin(form ->
                        form.loginPage("/member/login")
                                .usernameParameter("loginId")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/board/list")
                                .failureUrl("/member/login")
                                .successHandler(authenticationSuccessHandler)
                                .failureHandler(authenticationFailureHandler)

                )

                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/login")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout", "GET"))
                )
                .exceptionHandling(exception->exception
                        .accessDeniedHandler(accessDeniedHandler)
                )
        ;

        http    // 우리가 커스텀한 UserDetailsService를 이용하여 인증처리를 진행하도록 설정해준다.
                .userDetailsService(userDetailsService)
//                우리가 커스텀한 인증 공급자를 이요하여 인증처리를 진행하도록 설정해준다.
                .authenticationProvider(authenticationProvider());


        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }


}























