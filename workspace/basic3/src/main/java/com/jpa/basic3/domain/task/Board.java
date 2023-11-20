package com.jpa.basic3.domain.task;

import com.jpa.basic3.domain.base.Period;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "JPA_BOARD")
@Getter @Setter
@ToString(exclude = "user")
public class Board extends Period {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    private User user;
 
}
