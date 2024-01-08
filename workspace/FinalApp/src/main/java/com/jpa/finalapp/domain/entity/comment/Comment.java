package com.jpa.finalapp.domain.entity.comment;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public Comment(Long id, String content, Board board, Member member) {
        this.id = id;
        this.content = content;
        this.board = board;
        this.member = member;
    }
}
