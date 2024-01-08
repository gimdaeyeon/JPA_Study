package com.jpa.finalapp.domain.entity.comment;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Table(name = "JPA_COMMENT")
@SequenceGenerator(name = "SEQ_COMMENT_GENERATOR", sequenceName = "SEQ_COMMENT")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Period {
    @Id @GeneratedValue(generator = "SEQ_COMMENT_GENERATOR")
    @Column(name = "COMMENT_ID")
    private Long id;
    private String content;

    @JoinColumn(name = "BOARD_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
