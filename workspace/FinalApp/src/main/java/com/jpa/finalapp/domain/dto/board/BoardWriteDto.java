package com.jpa.finalapp.domain.dto.board;

import com.jpa.finalapp.domain.entity.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWriteDto {
    private Long boardId;
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private String loginId;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }

}
