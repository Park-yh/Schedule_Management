package com.example.todo.service;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public TodoResponse save(TodoRequest request) {
        Todo todo = new Todo(request.getTitle(), request.getContent(), request.getUsername(), request.getPassword());
        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                savedTodo.getUsername(),
                savedTodo.getCreatedAt(),
                savedTodo.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponse> findTodos(String username) {
        List<Todo> todos;
        if(StringUtils.hasText(username)){
            todos = todoRepository.findByUsernameOrderByModifiedAtDesc(username);
        } else{
            todos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));
        }
        return todos.stream()
                .map(todo -> new TodoResponse(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getUsername(),
                        todo.getCreatedAt(),
                        todo.getModifiedAt()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public TodoResponse findTodo(Long todoId){
        Todo todo = findTodoById(todoId);
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getUsername(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public TodoResponse updateTodo(Long todoId, TodoRequest request) {
        Todo todo = findTodoById(todoId);
        todo.updateTodo(request.getTitle(), request.getUsername());
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContent(),
                todo.getUsername(),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional
    public void deleteTodo(Long todoId){
        if(!todoRepository.existsById(todoId)){
            throw new IllegalArgumentException("TodoId not found!");
        }
        todoRepository.deleteById(todoId);
    }

    private Todo findTodoById(Long todoId){
        return todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("TodoId not found!")
        );
    }

}
