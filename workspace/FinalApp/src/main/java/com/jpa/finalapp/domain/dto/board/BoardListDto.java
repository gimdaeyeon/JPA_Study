package com.jpa.finalapp.domain.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class BoardListDto {
//      리스트 화면에 필요한 정보 : 게시물 번호, 제목, 조회수, 날짜, 회원 아이디
    private Long id;
    private String title;
    private int viewCount;
    private LocalDateTime createdDate;
    private String loginId;

    public BoardListDto(Long id, String title, int viewCount, LocalDateTime createdDate, String loginId) {
        this.id = id;
        this.title = title;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.loginId = loginId;
    }

    public String getCreatedDate(){
        return createdDate.format(DateTimeFormatter.ofPattern("yy/MM/dd"));
    }
}
