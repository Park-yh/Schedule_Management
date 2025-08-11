package com.example.todo.repository;

import com.example.todo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByNameOrderByModifiedAtDesc(String name);
}
