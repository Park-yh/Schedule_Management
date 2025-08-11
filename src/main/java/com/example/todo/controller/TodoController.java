package com.example.todo.controller;


import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/users/{usersId}/todos")
    public ResponseEntity<TodoResponse> createTodo(
            @PathVariable Long usersId,
            @RequestBody TodoRequest request
            ){
        TodoResponse response = todoService.save(request, usersId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{usersId}/todos")
    public ResponseEntity<List<TodoResponse>> getTodosByUser(@PathVariable Long usersId) {
        List<TodoResponse> responses = todoService.getTodosByUser(usersId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponse>> getAllTodos() {
        List<TodoResponse> responses = todoService.getAllTodos();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/users/{usersId}/todos/{todosId}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long usersId,
            @PathVariable Long todosId,
            @RequestBody TodoRequest request
    ) {
        TodoResponse response = todoService.updateTodo(todosId, request, usersId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{usersId}/todos/{todosId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long usersId,
            @PathVariable Long todosId
    ) {
        todoService.deleteTodo(todosId, usersId);
        return ResponseEntity.noContent().build();
    }


}
