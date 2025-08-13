package com.example.todo.service;

import com.example.todo.dto.CommentRequest;
import com.example.todo.dto.CommentResponse;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Todo;
import com.example.todo.entity.Users;
import com.example.todo.repository.CommentRepository;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByTodoId(Long todoId) {
        Todo todo = findTodoById(todoId);

        return todo.getComments().stream()
                .map(CommentResponse::new)
                .toList();
    }

    @Transactional
    public CommentResponse createComment(Long todoId, CommentRequest request, Long userId) {
        Todo todo = findTodoById(todoId);
        Users user = findUserById(userId);

        Comment comment = new Comment(request.getContent(), todo, user);
        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest request, Long userId) {
        Comment comment = findCommentById(commentId);

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        comment.updateComment(request.getContent());
        return new CommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = findCommentById(commentId);

        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    private Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() ->
                new IllegalArgumentException("선택한 할일은 존재하지 않습니다.")
        );
    }

    private Users findUserById(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다.")
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}