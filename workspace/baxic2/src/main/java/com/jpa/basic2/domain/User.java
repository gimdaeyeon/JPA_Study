package com.jpa.basic2.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "JPA_USER")
@Data
public class User {
    @GeneratedValue
    @Id private Long id;
    private String loginId;
    private String password;
    private String name;
    private int age;

//    양방향 매핑을 하는 경우
//    연관관계에 주인이 아닌쪽에 mappedBy를 사용한다.
//    반대쪽 엔티티의 필드 이름을 작성해준다.
    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "USER_ID")
    private List<Board> board = new ArrayList<>();
}
