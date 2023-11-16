<img src="../resource/영속성_컨텍스트2.png" width="70%" height="70%">

## 엔티티의 상태
1. 영속   
  영속성 컨텍스트에 저장된 상태   
  엔티티 매니저의 persist같은 메소드를 활용하면 된다.   
  영속상태가 되기 위해서는 반드시 식별자 값이 있어야한다.
   
2. 준영속   
   영속성 컨텍스트에 저정되어있다가 분리된 상태   
   영속성 컨텍스트가 관리하다가 어떤 이유로 더 이상 관리하지 않게되면 준영속 상태가 된다.   
   준영속 상태는 관리대상이 아니기 때문에 변경사항이 DB에 반영되지 않는다.
3. 비영속   
   영속성 컨텍스트와 무관한 상태   
   보통 엔티티 객체를 만들기만 하고 저장하지 않은 상태이다.   
   이 상태는 DB와 무관하기 때문에 수정을 해도 DB에 반영되지 않는다.
4. 삭제   
   엔티티를 영속성 켄텍스트와 DB에서 삭제한 상태이다.

#### 준영속 상태 엔티티를 다시 영속 상태로 만드는 방법
- entityManager.maerge(entity)   
  매개변수로 전달받은 엔티티를 영속상태로 만든다.   
  이 때 매겨변수의 엔티티는 준영속, 비영속 상태 둘 다 가능하다.

|user|board|
|:--:|:--:|
|번호(pk)|번호(pk)|
|아이디|제목|