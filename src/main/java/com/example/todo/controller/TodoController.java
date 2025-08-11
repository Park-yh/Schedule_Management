package com.example.todo.controller;


import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> createTodo(
            @RequestBody TodoRequest request
            ){
        return ResponseEntity.ok(todoService.save(request));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponse>> getTodos(@RequestParam(required = false) String author) {
        return ResponseEntity.ok(todoService.findTodos(author));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodos(
            @PathVariable Long todoId
    ) {
        return ResponseEntity.ok(todoService.findTodo(todoId));
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long todoId,
            @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, request));
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodo(
            @PathVariable Long todoId
    ) {
        todoService.deleteTodo(todoId);
    }


}
