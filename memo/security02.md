## 시큐리티 인증 프로세스 구성요소
![인증프로세스](./자료/시큐리티%20인증프로세스%20구성요소.jpg)

1. SecurityConfig 같은 사용자 저으이 설정 클래스를 만들고 설정을 한다.
2. HttpSecurity를 통해 formLogin(), logout() 같은 메소드로 설정을 하고 SecurityFilterChain을 반환한다.
3. SecurityFilterChain을 기반으로 새로운 필터가 생성, 등록된다.
    ```text
    formLogin()-> UsernamePasswordAuthenticationFileter 등록
    logout() -> LogoutFilter 등록
    ```

## 폼 로그인 인증 과정
![폼 로그인 이증 과정](./자료/로그인%20처리%20과정.jpg)
1. 사용자의 요청이 필터를 거친다.
2. UsernamePasswordAuthenticationFileter 에서 요청URL패턴을 검사한다.
3. 로그인처리 URL과 일치한다면 인증 객체(Authentication)를 생성한다.
     - 인증 객체는 로그인할 때 필요한 아이디, 패스워드를 가진다.
4. 인증 객체를 인증 관리자(AuthenticationManager)에게 넘겨준다.
5. 인증 관리자는 인증 공급자(AuthenticationProvider)를 이용하여 인증을 처리한다. 인증 공급자가 실질적인 인증처를 담다하는 객체이다.
6. 인증 공급자는 사용자 세부정보 서비스(UserDetailsService), 암호 인코더(PasswordEncoder)를 이용하여 사용자를 인증한다.
   - 사용자 세부정보 서비스로 사용자를 찾는다.
   - 암호 인코더로 암호를 검증한다.
7. 인증을 성공하면 인증 공급자는 자격증명을 갖춘 인증객체를 반환한다.
8. 인증객체를 반환받은 인증 매니저는 필터에 인증객체를 반환한다.
9. 인증을 마친 인증객체를 반환받은 필터는 해당 인증 객체를 보안 컨텍스트(SecurityContext)에 저장한다.
10. 인증에 성공했으므로 SuccessHandler를 실행한다.














