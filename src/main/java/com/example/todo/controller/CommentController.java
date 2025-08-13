package com.example.todo.controller;

import com.example.todo.dto.CommentRequest;
import com.example.todo.dto.CommentResponse;
import com.example.todo.entity.Users;
import com.example.todo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long todoId,
            @RequestBody CommentRequest request,
            @SessionAttribute(name = "LOGIN_USER") Users loginUser
    ) {
        CommentResponse responseDto = commentService.createComment(todoId, request, loginUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByTodoId(@PathVariable Long todoId) {
        List<CommentResponse> response = commentService.getCommentsByTodoId(todoId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @SessionAttribute(name = "LOGIN_USER") Users loginUser
    ) {
        CommentResponse response = commentService.updateComment(commentId, request, loginUser.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/todos/{todoId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @SessionAttribute(name = "LOGIN_USER") Users loginUser
    ) {
        commentService.deleteComment(commentId, loginUser.getId());
        return ResponseEntity.ok("댓글 삭제 성공");
    }
}