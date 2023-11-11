# 연관관계 매핑
JPA에서는 테이블과 엔티티를 매핑하여 사용한다.   
테이블은 다른 테이블과 연관 관계를 맺어 데이터를 표현기 때문에 이를 엔티티로도 표현해야한다.

```java

// 객체의 연관관계
public class User{
   Long userId;
   String userLoginId;
   String userPassword;
   String userName;
   Integer userAge;
}

public class Board{
   Long boardId;
   String boardTitle;
   String boardContent;
   User user;
}

```

### 1. 방향
- 단방향 : 객체의 관계에서 한 쪽의 개체만 다른 쪽을 참조하는 경우
- 양방향 : 양쪽 모두 참조하는 경우   
\* 방향의 개념은 객체에서만 존재한다. RDB에서는 양방향만 존재한다.

### 2. 다중성
- 한명의 회원은 여러 게시물을 작성 가능하므로 1:N관계라고 말할 수 있다.
- 1: N
- N:1
- 1:1
- N:N

### 3. 연관관계의 주인
- 객체간 양방향 관계를 만들게 되면 둘 중 하나의 객체가 연관관계의 주인이 되어야한다.



