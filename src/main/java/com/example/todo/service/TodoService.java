package com.example.todo.service;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<TodoResponse> findTodos(@RequestParam(required = false) String username) {
        List<Todo> todos;
        if(username != null && !username.isEmpty()){
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
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("TodoId not found!")
        );
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
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalArgumentException("TodoId not found!")
        );
        if(!todo.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 틀렸습니다!");
        }
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
        boolean b = todoRepository.existsById(todoId);
        if(!b) {
            throw new IllegalArgumentException("Todo not found!");
        }
        todoRepository.deleteById(todoId);
    }

}
