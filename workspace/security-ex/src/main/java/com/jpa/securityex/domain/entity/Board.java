package com.jpa.securityex.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "SEC_BOARD")
@SequenceGenerator(name = "SEQ_BOARD_GENERATOR", sequenceName = "SEQ_SEC_BOARD")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue(generator = "SEQ_BOARD_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;
    private String content;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}



