package com.jpa.finalapp.domain.dto.board;

import com.jpa.finalapp.domain.entity.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardEditDto {
    private Long boardId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String loginId;

    public static BoardEditDto from(Board board){
        BoardEditDto boardEditDto = new BoardEditDto();
        boardEditDto.setBoardId(board.getId());
        boardEditDto.setTitle(board.getTitle());
        boardEditDto.setContent(board.getContent());
        if (board.getMember() !=null){
            boardEditDto.setLoginId(board.getMember().getLoginId());
        }

        return boardEditDto;
    }
}
