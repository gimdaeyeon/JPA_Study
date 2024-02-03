## Authentication(인증 객체)
- 사용자의 인증 정보를 저장하는 객체이며, 일종의 토큰역할을 한다.
- 인증 단계이서는 id와 password를 저장하며, 인징이 끝나면 사용자에 대한 정보, 권한정보를 저장하게 된다.
- 인증이 끝난 인증 객체는 SecurityContext에 저장되며, 전역에서 참조가 가능하다.


### Token(토큰)
웹 상에서 토큰은 본인 확인 수단을 의미한다.(권한을 증명하는 수단)   
즉, 특정 사용자와 관련된 데이터를 담는 용도로 사용한다.   
대표적으로 JWT가 있다.

### 인증 객체의 주요 속성
1. Authenticated: 인증 여부(boolean)
2. Principal: 인증이 되었다면 UserDetails객체를 저장하고 있다.
3. Credentials: 비밀번호
4. Authorities: 사용자의 권한들

### SecurityContext
- 인증 객체를 저장하는 객체이다.
- 전역에서 참조가 가능하므로 어디서든 인증객체를 꺼내 사용가능하다.
- HttpSession을 통해서 사용할 수 있다.

SecurityContextPersistenceFilter가 SecurityContext를 세션에 자동 저장하기 때문이다. SecurityContext에는 인증이 끝난 사용자 정보가 들어있으므로 로그인 처리에서 우리가 직접 세션에 회언 번호같은 정보를 저장할 필요가 없다.

### SecurityContextHolder
- SecurityContext를 저장, 관리한다.
- 여러 static 메서드를 지원하므로 어디서든 객체생성 없이 기능을 사용할 수 있다.

```text
SecurityContextHolder--내부에-->
SecurityContext --내부에--> Authentication
```














