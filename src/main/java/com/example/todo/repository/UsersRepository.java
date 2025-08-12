package com.example.todo.repository;

import com.example.todo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByNameOrderByModifiedAtDesc(String name);
    Optional<Users> findByEmail(String email);
}
