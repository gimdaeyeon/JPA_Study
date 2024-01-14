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

    /*
    Boaard 엔티티와 관련된 수정 로직
    단순히 엔티티의 상태만을 변경하는 기능은 엔티티의 메소드로 만드는 것이 객체지향 설계에 가깝다.
    메소드의 이름은 의미가 명확하게 만들어준다.
    이 때 수정된 내용과 타이틀을 매개변수로 받기 위해 Dto를 사용하는 것은 되도록 지양한다.
    엔티티는 최대한 순수한 상태를 유지하는게 좋으므로 다른 객체에 의존하지 않도록 노력한다.

    참고 : set을 절대 사용하면 안된다는 것이 아니라 모든 값 변경을 set으로 처리한다는 것이
    바람직하지 않다는 의미이다.
    set만을 사용한다면 수정을 위한 것인지 생성 후 초기화를 위한 것인지 알 수 없다.
    메소드의 이름을 명확하게 만들기 애매하다면 set을 사용해도 된다.
    (특히 양방향 관계 설정에서 애매한 상황이 많이 나타난다.)
     */

//    게시글 수정을 위한 메소드
    public void modifyBoardDetail(String title, String content){
        this.title = title;
        this.content = content;
    }

//    게시글 조회수 증가를 위한 메소드
    public void increaseViewCount(){
        this.viewCount++;
    }

//    작성자를 설정하기 위한 메소드
    public void setAuthor(Member member){
        this.member = member;
    }

}








