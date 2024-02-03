package com.jpa.finalapp.api.controller.comment;

import com.jpa.finalapp.api.dto.comment.CommentDto;
import com.jpa.finalapp.api.dto.common.ApiResponse;
import com.jpa.finalapp.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/v1/boards/{boardId}/comments")
    public void commentWrite(@PathVariable("boardId") Long boardId,
                             @RequestBody CommentDto.Request commentDtoReq,
                             @SessionAttribute("memberId") Long memberId) {
        commentDtoReq.setBoardId(boardId);
        commentService.registerComment(commentDtoReq, memberId);
    }

    @PostMapping("/v2/boards/{boardId}/comments")
    public HttpEntity<ApiResponse<Object>> commentWriteV2(@PathVariable("boardId") Long boardId,
                                                          @RequestBody CommentDto.Request commentDtoReq,
                                                          @SessionAttribute("memberId") Long memberId) {
        commentDtoReq.setBoardId(boardId);
        CommentDto.Response savedDto = commentService.registerComment(commentDtoReq, memberId);

        ApiResponse<Object> apiResPonse = ApiResponse.builder()
                .data(savedDto)
                .success(true)
                .message("작성이 완료되었습니다")
                .build();
//        1. 헤더에 방금 생성된 자원의 위치를 담은 Location을 저장한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:10000/api/v5/comments/" + savedDto.getCommentId());

//        2. 바디에 방금 생성된 자원을 담아준다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(apiResPonse);
    }

    @PostMapping("/v3/boards/{boardId}/comments")
    public HttpEntity<ApiResponse<Object>> commentWriteV3(@PathVariable("boardId") Long boardId,
                                                          @RequestBody CommentDto.Request commentDtoReq,
                                                          @SessionAttribute("memberId") Long memberId) {
        commentDtoReq.setBoardId(boardId);
        CommentDto.Response savedDto = commentService.registerComment(commentDtoReq, memberId);

        ApiResponse<Object> apiResPonse = ApiResponse.builder()
                .data(savedDto)
                .success(true)
                .message("작성이 완료되었습니다")
                .build();

//        Location을 만드는 다른 방법
        URI locationUri = ServletUriComponentsBuilder   // 스프링에서 지원하는 크래스, 현재 HTTP요청을 기반으로 URI를 생성해준다.
//                ContextPath -> 어플리케이션 루트경로를 의미(우리는 없음) -> http://localhost:10000/루트경로
//                현재 서버주소 + contextPath를 기반으로 URI를 빌드한다는 의미
                .fromCurrentContextPath()
//                path()로 경로를 추가해준다.
                .path("/v5/comments/{commentId}")
                .buildAndExpand(savedDto.getCommentId())
                .toUri();

        return ResponseEntity.created(locationUri)
                .body(apiResPonse);
    }


//    ==============================================================

    @GetMapping("/v1/comments/{commentId}")
    public CommentDto.Response commentFindOneV1(@PathVariable("commentId") Long commentId) {
        CommentDto.Response commentDtoResp = commentService.findOne(commentId);

        return commentDtoResp;
    }

    @GetMapping("/v2/comments/{commentId}")
    public ResponseEntity<CommentDto.Response> commentFindOneV2(@PathVariable("commentId") Long commentId) {
        CommentDto.Response commentDtoResp = commentService.findOne(commentId);
//        스프링에서는 Servlet을 직접 다루거나 Servlet에서 사용하던 저수준 기술 사용ㅇ을 지양하기 때문에
//        다른 객체를 지원해준다. -> ResponseEntity

//        ResponseEntity를 사용하는 방법은 크게 2가지가 있다.
//        1. 생성자를 통한 직접 생성
//        2. 정적 팩토리 메소드(권장함)

        HttpHeaders headers = new HttpHeaders(); // 헤더는 상황에 따라 생략 가능
        headers.add("Custom-Header", "test");

//        1. 생성자를 통한 직접 생성
        return new ResponseEntity<>(commentDtoResp, headers, HttpStatus.OK);
    }

    @GetMapping("/v3/comments/{commentId}")
    public ResponseEntity<CommentDto.Response> commentFindOneV3(@PathVariable("commentId") Long commentId) {
        CommentDto.Response commentDtoResp = commentService.findOne(commentId);

        HttpHeaders headers = new HttpHeaders(); // 헤더는 상황에 따라 생략 가능
        headers.add("Custom-Header", "test");

//        2. 정적 팩토리 메소드(ok() : 200, notFound() : 404 등등)
//        정적 팩토리 메소드를 사용하면 Builder를 반환 headers와 body를 빌더패턴으로 작성가능
//        - body()를 마지막에 사용하면 ResponseEntity가 빌드되어 반환된다.

//        - 헤더 설정이 필요없다면 ok(body) 를 사용하면 바로 ResponseEntity가 빌드됨

//        - 바디설정이 필요없다면 headers()만 설정 후 build() 를 사용하면 ResponseEntity 가 빌드됨
        return ResponseEntity.ok()
                .headers(headers)
                .body(commentDtoResp);
    }

    @GetMapping("/v4/comments/{commentId}")
    public ResponseEntity<CommentDto.Response> commentFindOneV4(@PathVariable("commentId") Long commentId) {
        CommentDto.Response commentDtoResp = commentService.findOne(commentId);

        HttpHeaders headers = new HttpHeaders(); // 헤더는 상황에 따라 생략 가능
        headers.add("Custom-Header", "test");

//        status()를 사용하면 HttpStatus 객체에 저장된 정적 상수를 활용하여 상태코드 직접 설정가능
//        여러 상태 코드
//        200 : ok
//        201 : created
//        202 : Accepted
//        400 : Bad Request
//        401 : Unauthorized

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .build();
    }

    @GetMapping("/v5/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto.Response>> commentFindOneV5(@PathVariable("commentId") Long commentId) {
        CommentDto.Response commentDtoResp = null;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "test");
        try {
            commentDtoResp = commentService.findOne(commentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(new ApiResponse<>(true, "조회완료", commentDtoResp));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "없음", null));
        }
    }

    //    ===========================================================
    @PatchMapping("/v1/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentDto.Response>> commentModify(@PathVariable("commentId") Long commentId,
                                                                          @RequestBody @Valid CommentDto.Request commentDtoReq,
                                                                          BindingResult result) {
        if (result.hasErrors()) {
            ApiResponse<CommentDto.Response> apiRespFail = new ApiResponse<>(false, "수정이 실패됐습니다.", null);
            return ResponseEntity.badRequest().body(apiRespFail);
        }

        commentDtoReq.setCommentId(commentId);
        CommentDto.Response commentDtoResp = commentService.modifyComment(commentDtoReq);

        ApiResponse<CommentDto.Response> apiResponse = new ApiResponse<>(true, "수정이 완료되었습니다", commentDtoResp);

//        수정에서는 일반적으로 Location설정 안함.
//        보통 바디에 수정된 데이터만 담아서 보내거나 Ok(200) 또는 NoContent(204) 상태코드만 보냄
        return ResponseEntity.ok(apiResponse);
    }

    //    =======================================================
    @GetMapping("/v1/boards/{boardId}/comments")
    public ResponseEntity<ApiResponse<Slice<CommentDto.Response>>> commentList(@PathVariable("boardId") Long boardId,
                                                                               @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Slice<CommentDto.Response> commentSlice = commentService.commentListWithSlice(boardId, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "목록 조회 성공", commentSlice));
    }


}






















