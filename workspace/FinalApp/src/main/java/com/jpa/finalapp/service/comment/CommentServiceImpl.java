package com.jpa.finalapp.service.comment;

import com.jpa.finalapp.api.dto.comment.CommentDto;
import com.jpa.finalapp.domain.entity.board.Board;
import com.jpa.finalapp.domain.entity.comment.Comment;
import com.jpa.finalapp.domain.entity.member.Member;
import com.jpa.finalapp.repository.board.BoardRepository;
import com.jpa.finalapp.repository.comment.CommentRepository;
import com.jpa.finalapp.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto.Response> commentListWithPage(Long boardId, Pageable pageable) {
        Page<CommentDto.Response> commentPage = commentRepository.commentListWithPage(boardId, pageable)
                .map(CommentDto.Response::from);
        return commentPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<CommentDto.Response> commentListWithSlice(Long boardId, Pageable pageable) {
        return commentRepository.commentListWithSlice(boardId,pageable)
                .map(CommentDto.Response::from);
    }

    @Override
    public CommentDto.Response registerComment(CommentDto.Request commentDtoReq, Long memberId) {
//        Comment 엔티티를 insert하기 위해서는 연관관계를 맺은 member, board 엔티티가 필요하다.
//        그러기 위해 실제 엔티티를 조회한다면 select쿼리가 발생된다.
//        연관관계가 많을 수록 더 많은 select쿼리가 발생될 것이며 상황에 따라 비효율적일 수 있다.
//        이런 경우 실제 엔티티 객체를 Comment에 저장하는 것이 아니라 먼저 id만 가진 프록시 객체를 넣어서
//        해결할 수 있다.
        Board boardProxy = boardRepository.getReferenceById(commentDtoReq.getBoardId());
        Member memberProxy = memberRepository.getReferenceById(memberId);
        Comment comment = Comment.builder()
                .content(commentDtoReq.getContent())
                .board(boardProxy)
                .member(memberProxy)
                .build();
        Comment savedComment = commentRepository.save(comment);

        return CommentDto.Response.from(savedComment);
    }

    @Override
    public CommentDto.Response modifyComment(CommentDto.Request commentDtoReq) {
        Comment foundComment = commentRepository.findById(commentDtoReq.getCommentId())
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 댓글"));
        foundComment.modifyContent(commentDtoReq.getContent());

        return CommentDto.Response.from(foundComment);
    }

    @Override
    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto.Response findOne(Long commentId) {
        Comment foundComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 댓글"));

        return CommentDto.Response.from(foundComment);
    }
}









