package com.jpa.finalapp.service.comment;

import com.jpa.finalapp.api.dto.comment.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {
    Page<CommentDto.Response> commentListWithPage(Long boardId, Pageable pageable);
    Slice<CommentDto.Response> commentListWithSlice(Long boardId, Pageable pageable);
    CommentDto.Response registerComment(CommentDto.Request commentDtoReq, Long memberId);
    CommentDto.Response modifyComment(CommentDto.Request commentDtoReq);
    void removeComment(Long commentId);
    CommentDto. Response findOne(Long commentId);

}










