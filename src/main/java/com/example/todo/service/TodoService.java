package com.example.todo.service;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;
import com.example.todo.entity.Users;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public TodoResponse save(TodoRequest request, Long userId) {
        Users user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        Todo todo = new Todo(request.getTitle(), request.getContent(), user);
        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(savedTodo);
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByUser(Long userId) {
        Users user = findUserById(userId);
        return todoRepository.findByUserOrderByModifiedAtDesc(user)
                .stream()
                .map(TodoResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"))
                .stream()
                .map(TodoResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoResponse getTodo(Long todoId){
        Todo todo = findTodoById(todoId);
        return new TodoResponse(todo);
    }

    @Transactional
    public TodoResponse updateTodo(Long todoId, TodoRequest request, Long usersId) {
        Todo todo = findTodoById(todoId);
        if (!Objects.equals(todo.getUser().getId(), usersId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        todo.update(request.getTitle(), request.getContent());

        return new TodoResponse(todo);
    }

    @Transactional
    public void deleteTodo(Long todoId, Long usersId){
        Todo todo = findTodoById(todoId);
        if (!Objects.equals(todo.getUser().getId(), usersId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        todoRepository.delete(todo);
    }

    private Todo findTodoById(Long todoId){
        return todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("TodoId not found!")
        );
    }

    private Users findUserById(Long userId) {
        return usersRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("선택한 유저는 존재하지 않습니다.")
        );
    }

}
