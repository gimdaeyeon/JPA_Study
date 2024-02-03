package com.jpa.security02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetailsService는 사용자 정보(UserDetails)를 조회하는 객체이다.

//        UserDetails는 사용자의 정보를 담는 객체이다.
//        UserDetails는 인터페이스이므로 이를 구현한 객체가 필요하다.
//        시큐리티에서 기본제공해주는 객체가 User객체이며, 아이디, 패스워드, role정보만 담을 수 있다.
//        많은 정보를 담을 수 없기 때문에 실제 사용시에는 UserDetails 인터페이스를 직접 implements한
//        클래스를 만들어 사용한다.
        UserDetails userDetails1 = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("1234")
                .roles("USER")
                .build();
        UserDetails userDetails2 = User.withDefaultPasswordEncoder()
                .username("user2")
                .password("1234")
                .roles("ADMIN")
                .build();

        UserDetails userDetails3 = User.withDefaultPasswordEncoder()
                .username("user3")
                .password("1234")
                .roles("USER","ADMIN")
                .build();

//        아직 DB연동을 안했기 때문에 메모리에 회원 정볼를 저장해두고 사용한다.
//        실제 사용시에는 DB에 접근하여 회원 정보를 다뤄야 하므로 UserDetailService 인터페이스를
//        직접 implements한 클래스를 만들어 사용한다.
        return new InMemoryUserDetailsManager(userDetails1,userDetails2,userDetails3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(authorize->
                        authorize
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/logout").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // 사용자의 특정 Role만 허용
                                .requestMatchers("/board/**").hasAnyRole("ADMIN","USER")
                                .anyRequest().permitAll()
                );

        http
                .formLogin(form->
                        form.defaultSuccessUrl("/")

                        );

        return http.build();
    }


}














