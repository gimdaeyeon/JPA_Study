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

    @OneToMany(mappedBy = "user")
    List<Board> boards = new ArrayList<>();
}
