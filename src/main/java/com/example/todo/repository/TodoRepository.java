package com.example.todo.repository;

import com.example.todo.entity.Todo;
import com.example.todo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserOrderByModifiedAtDesc(Users users);
}
