package com.jpa.finalapp.domain.dto.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardEditDto {

    private Long boardId;
    private String title;
    private String content;
}
