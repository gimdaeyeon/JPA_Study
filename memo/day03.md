# 연관관계 매핑

jpa에서는 테이블과 엔티티를 매핑하여 사용한다.  
테이블은 다른 테이블과 관계를 맺어 데이터를 표현하기 때문에  
이를 엔티티로도 표현해야한다.

### 테이블 연관관게

|    USER     |     BOARD     |
| :---------: | :-----------: |
| 회원 번호PK | 게시물번호 PK |
|   아이디    |     제목      |
|  비밀번호   |     내용      |
|    이름     |  회원 번호FK  |
|    나이     |               |

### 객체의 연관관계

```java
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

1. 방향

- 단방향  
   객체의 관계에서 한 쪽의 객체만 다른 쪽을 참조하는 경우
- 양방향  
  양쪽 모두 참조하는 경우 양방향 관계라고 한다.

  **방향의 개념은 객체에서만 존재한다.**

1. 다중성   
   한 명의 회원은 여러 게시물을 작성 가능하므로 1:N관계라고 한다.
- 1:N
- N:1
- 1:1
- N:N

3. 연관관계의 주인   
   객체간 양방향 관계를 만들게되면 둘 중 하나의 객체가   
   연관관계의 주인이 되어야한다.












