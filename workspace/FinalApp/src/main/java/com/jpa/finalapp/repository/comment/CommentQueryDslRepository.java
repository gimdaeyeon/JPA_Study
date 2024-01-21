package com.jpa.finalapp.repository.comment;

import com.jpa.finalapp.domain.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentQueryDslRepository {
    Page<Comment> commentListWithPage(Long bardId, Pageable pageable);

    Slice<Comment> commentListWithSlice(Long bardId, Pageable pageable);
}
