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
@Getter @Setter @ToString
public class User extends Period {
    @Id @GeneratedValue
    private Long id;
    private String loginId;
    private String password;
    private int age;

    @OneToMany(mappedBy = "user")
    List<Board> boards = new ArrayList<>();
}
