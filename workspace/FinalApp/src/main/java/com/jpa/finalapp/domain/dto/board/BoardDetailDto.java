package com.jpa.finalapp.domain.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class BoardDetailDto {
    private Long boardId;
    private String title;
    private String content;
    private String loginId;
    private String createdDate;
    private int viewCount;
    private Long memberId;
    private Long boardCount;
    private Long commentCount;

    public BoardDetailDto(Long boardId, String title, String content, String loginId, String createdDate, int viewCount, Long memberId, Long boardCount, Long commentCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.loginId = loginId;
        this.createdDate = createdDate;
        this.viewCount = viewCount;
        this.memberId = memberId;
        this.boardCount = boardCount;
        this.commentCount = commentCount;
    }
}
