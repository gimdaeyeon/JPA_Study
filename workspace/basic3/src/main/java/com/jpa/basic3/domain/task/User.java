package com.jpa.basic3.domain.task;

import com.jpa.basic3.domain.base.Period;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "JPA_USER")
@Getter @Setter @ToString(exclude = "boards")
@SequenceGenerator(name = "SEQ_USER_GENERATOR" , sequenceName = "SEQ_JPA_USER")
public class User extends Period {
    @Id @GeneratedValue(generator = "SEQ_USER_GENERATOR")
    @Column(name = "USER_ID")
    private Long id;
    private String loginId;
    private String password;
    private int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL
//     orphanRemoval = true -> 고아객체가 생겼을 때 자동으로 삭제한다.
//     orphanRemoval를 사용할 떄는 케스케이드를 ALL이나 Persist로 설정을 해야한다(Hibernate 버그)
            ,orphanRemoval = true)
    List<Board> boards = new ArrayList<>();
}
