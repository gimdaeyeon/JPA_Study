package src.builder;

class User {
    private Long id;
    private String name;
    private int age;
    private String email;

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    //    빌더 클래스는 static 내부 클래스로 생성한다.
    public static class Builder {
        //        빌더 클래스는 동일한 이름의 필드를 가진다.
        private Long id;
        private String name;
        private int age;
        private String email;

        //        각 필드 이름과 동일한 메소드를 가진다.
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }


    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", age=" + age +
               ", email='" + email + '\'' +
               '}';
    }
}


public class Main {
    public static void main(String[] args) {

        User user = new User.Builder()
                .id(1L)
                .name("김철수")
                .email("aaa@naver.com")
                .age(22)
                .build();
        System.out.println("user = " + user);

//        빌더 패턴은 생성자 패턴의 불변성 보장이라는 장점과, setter를 사용하는 자바 bean패턴의 원하는 필드만
//        초기화 한다는 장점을 모두 가지게 된다.
//        그렇지만 항상 좋은 것은 아니다. 단점도 존재한다.
//        코드의 복잡성이 증가되고, User객체를 생성하기 위해서 Builder객체도 만들어야한다는 단점이 있다.
//        필드가 적고 객체 생성을 많이 하지 않는다면 빌더 패턴을 억지로 쓸 필요가 없다.

    }

}








