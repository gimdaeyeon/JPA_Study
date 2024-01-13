package com.jpa.finalapp.domain.entity.board;

import com.jpa.finalapp.domain.base.Period;
import com.jpa.finalapp.domain.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "JPA_BOARD")
@SequenceGenerator(name = "SEQ_BOARD_GENERATOR", sequenceName = "SEQ_BOARD")
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends Period {
    @Id @GeneratedValue(generator = "SEQ_BOARD_GENERATOR")
    @Column(name = "BOARD_ID")
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    @JoinColumn(name = "MEMBER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Board(Long id, String title, String content, int viewCount, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.member = member;
    }
}








