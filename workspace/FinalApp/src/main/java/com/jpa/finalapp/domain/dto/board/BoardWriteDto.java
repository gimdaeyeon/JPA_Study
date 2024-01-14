package com.jpa.finalapp.domain.dto.board;

import com.jpa.finalapp.domain.entity.board.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWriteDto {
    private Long boardId;
    private String title;
    private String content;
    private String loginId;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

}
