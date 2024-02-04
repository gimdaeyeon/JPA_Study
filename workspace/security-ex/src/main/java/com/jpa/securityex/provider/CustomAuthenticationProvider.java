package com.jpa.securityex.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    //    인증 공급자는 두 가지 객체를 활용하여 인증을 처리한다고 했다.(로그인 처리 그림 참고)
//    바로 UserDetailsService와 PasswordEncoder 객체이다.
//    여기서 주입되는 객체는 우리가 커스텀하고 등록한 객체이다.
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        인증 방식을 구현하는 메서드(인증 로직을 직접 설정할 수 있다.)
//        우리는 기본 인증 공급자가 처리하는 방식을 비슷하게 구현하여 내부 원리를 파악할 것이다.

//        인증 공급자는 UserDetailsService의 loadUserByUsername()을 실행하여 사용자의 정보를 가져온다.
        String password = (String)authentication.getCredentials();
        MemberDetails memberDetails = (MemberDetails) userDetailsService.loadUserByUsername(authentication.getName());

        if(!passwordEncoder.matches(password,memberDetails.getPassword())){
            throw new BadCredentialsException("비밀번호 불일치");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDetails, memberDetails.getPassword(), memberDetails.getAuthorities());

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        우리가 재정의하는 인증 공급자가 허용하는 인증객체의 타입을 지정한다.
//        Authentication은 인터페이스이며, 이를 구현한 여러 클래스가 존잰한다.
//        로그인에서 사용하는 인증 객체 구현체는 UsernamePasswordAuthenticationToken 을 사용한다.
//        즉, 우리가 만드는 인증 공급자가 UsernamePasswordAuthenticationToken 타입을 허용하도록 설정해줘야한다.
//        return false; 로 설정되면 인증 공급자는 어떠한 인증 객체도 처리할 수 없다.

//        isAssignableFrom()은 타입이 대체가능한지 검사하는 메서드(쉽게 말하면 공통 타입을 가졌는지 검사)
//        B 클래스가 A클래스를 상속받았다면
//        A.class.isAssignableFrom(B.class) -> B클래스를 A타입으로 대체가능? ->true
//        B.class.isAssignableFrom(A.class) -> A클래스를 B타입으로 대체가능? ->false
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
