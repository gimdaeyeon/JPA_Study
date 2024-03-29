# Security

## State
### Stateful(상태유지)
- 클라이언틍와 서버의 관계에서, 서버가 클라이언트의 상태 정보를 기억하는 것을 의미한다.
- 상태 정보를 기억함으로써 추가 요청 처리시 이전 상태를 기반으로 더 효율적으로 처리할 수 있다.
- 그러나 많은 클라이언트가 동시에 서비스를 이요할 경우, 각 클러이언트의 상태 정보를 모두 저장하고 관리해야하기 때문에 서버에 부담이 될 수 있다.

```text
예 : 사용자가 로그인하면 사용자의 아이디, 이름, 주소등의 정보를 세션에 저장하고 필요시 이 정보를 사용
```

### Stateless(무상태)
- 서바가 클리이언트의 상태를 기억하지 않는것을 의미한다.
- 서버에 저장되는 상태 정보가 없으므로 서버의 부담이 줄어든다.
- 하지만, 클라이언트는 자신의 신분(예: 로그인 여부)을 매 요청마다 서버에 전달해야하며, 이는 각 요청이 독립적이라는 의미이다.
- Stateless의 장점 중 하나는 클라이언트를 기억하지 않기 때문에, 분산 시스템(클라우드 등)이나 여러 서버로 구성된 환경(MSA등)에서도 요청을 처리하는데 문제가 없다.

## HTTP의 특징
1. HTTP는 클라이언트 - 서버 구조이며, 클라이언트가 요청을 보낸 후 대기하고 서버가 요청을 처리하여 응답을 보낸다.(단순함이 장점이며, 확장 가능성이 높다.)
2. HTTP는 기본적으로 Stateless, Connectionless(비연결성) 프로토콜이다.   
즉, 클라이언트가 요청을 보낸 후 응답을 받으면 연결이 끊기고 서버에서는 방금 받은 요청 정보를 기억하지 않는다.

### Stateless의 단점과 해결방안
클라이언트의 상태를 기억하지 않으니 이 클라이언트가 로그인을 했는지 알 수가 없다. 그래서 우리는 Session, Cookie, 토큰(jwt 등)을 활용하여 state를 기억하는 방식을 사용한다.



## 인증과 인가

### 인증(Authentication) : 이용자를 식별하기 위한 절차
예 : id,pw를 받아서 우리 회원이 맞는지 확인

### 인가(Authorization) : 인증된 사용자에게 권한을 확인하는 절차
예 : 이 회원은 어드민 회원이니까 관리자 페이지에 진입 가능

### 로그인 처리 인증/인가 흐름
1. 클라이언트의 로그인 요청   
      - 클라이언트가 ID와 PW를 입력하여 서버에 로그인 요청을 보낸다
2. 서버의 인증 절차
    - 서버는 제공된 ID, PW를 확인하여 클라이언트를 검증한다.
3. 권한 부여 및 세션/쿠키/토큰 발급
    - 인증에 성공하면, 서버는 사용자의 권한을 확인하고, 해당 권한에 맞는 세션, 쿠키 또는 토큰드을 발급한다.
4. 인증된 클라이언트의 요청
   - 인증된 클리이언트는 이후 요청 시 발급받은 쿠키나 토큰을 요청에 포함시켜 보낸다.
   - HTTP는 Stateless이기 때문에 클라이언트의 인증을 검증할 수 있는 쿠키나 토큰을 매 요청에 포함시켜야 한다.
5. 서버의 권한 확인
   - 서버는 요청을 받을 때마다 포함된 쿠키나 토큰을 확인하여 사용자의 권한을 검증한다.
6. 서버의 응답
   - 권한이 유효하다면, 서버는 적절한 응답을 보낸다.


### 세션 인증과 토큰 인증
- #### 세션 인증
  - 세션은 서버 메모리에 저장, 관리하기 때문에 사용이 편하다.
  - 클라이언트의 상태를 서버에서 저장하므로 클라이언트 수가 많아지면 메모리의 부하가 우려된다.
  - 만약 우리 어플리케이션의 서버가 여러 서버로 분산되어있다면 각 서버는 세션을 따로 관리하므로 세션이 공유되지 않아 문제가 생긴다.(해결 방법이 필요)
- #### 토큰 인증
  - 토큰은 세션과 달리 클라이언트 정보를 서버에 저장하지 않으므로 서버 메모리에 부담되지 않는다.
  - 클라이언트 쪽에 저장되므로 암호화가 풀릴 가능성이 있으며, 해커에게 토큰을 탈취당하면 실질적인 사용자 데이터는 쉽게 확인이 가능하다.(암호화 되어도 민감한 정보는 저장하면 안됨)
  - 서버가 분산되어 있어도 부담되지 않는다.


- ### 참고
  JWT는 다음과 같은 구조를 가진다.   
  `header.payload.signature`
  - header: 토큰을 어떻게 검증하는지에 대한 내용
  - payload: 실질적인 데이터(사용자 정보, 권한 등등)
  - signature : secret key로 암호화된 데이터이며 토큰을 변조하기 어렵게 하기위해 필요하다.

## URL의 구조
http://www.koreait.com/academy/centerinfo?page=1

- 프로토콜 : http
- 도메인(호스트 주소와 포트번호) : www.koreait.com(123.123.123.123:8080)
- 경로: /academy/centerinfo
- 쿼리스트링: ?page=1


## 웹 어플리케이션의 대표적인 2가지 취약점
### CORS(Cross-Origin Resource Sharing)
- 교차 출처 리소스 공유
- 출처란 프로토콜 + 도메인을 의미한다.
- 즉, 다른 출처간 리소스를 공유, 교환하는 것을 의미함.

기본적으로 리소스 공유를 허용하면 보안에 취약하므로 막는것이 원칙이지만 요즘의 어플리케이션은 여러 서버를 두고 구현하기 때문에 리소스를 공유하지 않으면 제약이 많다. 그래서 이를 허용하는 정책을 만든것이 CORS 정책이다.


###  CSRF(Cross Site Request Forgery)
- **사이트간 요청 위조**   
예시:
1. 클라이언트가 쇼핑몰에 로그인한다.
2. 쇼핑몰이 클라이언트에게 로그인을 인증하는 쿠키를 보내준다.
3. 클라이언트가 쇼핑을 하던 중 해커가 만든 악성링크를 누른다.
4. 악성링크를 누르자마자 쇼핑몰에 비밀번호 변경 같은 요청을 보낸다.
5. 서버는 요청에 클라이언트 로그인을 인증하는 쿠키가 올바르게 담겨있으니 요청을 처리하여 비밀번호를 변경한다.

- **해결 방법**
- 해당 요청이 우리 사이트의 UI를 통해 보내진 것인지 확인한다.
  1. 클라이언트가 쇼핑몰에 로그인한다.
  2. 쇼핑몰에 클라이언트에게 로그인을 인증하는 쿠키를 보낸다. 이 때 CSRF토큰을 같이 저장하여 보내준다.
  3. CSRF토큰은 HTML폼 내부에 몰래 삽입된다.
  4. 이후 요청에서 숨겨진 토큰이 같이 보내지게된다.
  5. 서버는 요청에 토큰 존재여부를 판단하여 해당 요청이 우리 UI를 통해 온것인지 악성 링크를 토애 온것인지 판단할 수 있다.
  












