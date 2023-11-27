package com.jpa.jpql.domain2;

import com.jpa.jpql.embedded.Address;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "JPA_MEMBER")
@Data
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    @Embedded // @Embeddable클래스를 필드로사용할 때 작성한다.
    private Address address;
    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<>();
}
