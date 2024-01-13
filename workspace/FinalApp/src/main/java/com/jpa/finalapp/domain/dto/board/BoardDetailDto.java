package com.jpa.finalapp.domain.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class BoardDetailDto {
    private Long boardId;
    private String title;
    private String content;
    private String loginId;
    private int viewCount;
    private Long boardCount;
    private Long commentCount;

    public BoardDetailDto(Long boardId, String title, String content, String loginId, int viewCount, Long boardCount, Long commentCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.loginId = loginId;
        this.viewCount = viewCount;
        this.boardCount = boardCount;
        this.commentCount = commentCount;
    }
}
