package com.jpa.finalapp.api.dto.comment;

import com.jpa.finalapp.domain.entity.comment.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class CommentDto {


    @Data
    @NoArgsConstructor
    public static class Request{
        private Long commentId;
        @NotBlank
        private String content;
        private Long boardId;
        private Long memberId;
    }
    @Data
    @NoArgsConstructor
    public static class Response{
        private Long commentId;
        private String content;
        private String createdDate;
        private Long boardId;
        private String loginId;
        private Long memberId;

        public static Response from(Comment comment){
            Response response = new Response();
            response.setCommentId(comment.getId());
            response.setContent(comment.getContent());
            response.setCreatedDate(comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

            if(comment.getMember() !=null){
                response.setLoginId(comment.getMember().getLoginId());
                response.setMemberId(comment.getMember().getId());
            }

            if(comment.getBoard() !=null){
                response.setBoardId(comment.getBoard().getId());
            }

            return response;
        }
    }

}




