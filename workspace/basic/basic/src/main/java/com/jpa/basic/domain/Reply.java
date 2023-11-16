package com.jpa.basic.domain;

import com.jpa.basic.domain.type.ReplyStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "JPA_REPLY")
@Data
public class Reply {
    @Id @GeneratedValue
    private Long id;
    private String content;
    private String writer;
    @Enumerated(EnumType.STRING)
    private ReplyStatus replyStatus;
}
