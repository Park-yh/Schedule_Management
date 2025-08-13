package com.example.todo.controller;

import com.example.todo.dto.UsersRequest;
import com.example.todo.dto.UsersResponse;
import com.example.todo.service.UsersService;
import com.example.todo.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/users")
    public ResponseEntity<UsersResponse> createUsers(@Valid @RequestBody UsersRequest request) {
        return ResponseEntity.ok(usersService.createUsers(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersResponse>> findUsers(
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(usersService.findUsers(name));
    }

    @GetMapping("/users/{usersId}")
    public ResponseEntity<UsersResponse> findUser(@PathVariable Long usersId) {
        return ResponseEntity.ok(usersService.findUser(usersId));
    }

    @PutMapping("/users/{usersId}")
    public ResponseEntity<UsersResponse> updateUsers(
            @PathVariable Long usersId,
            @RequestBody UsersRequest request
    ) {
        return ResponseEntity.ok(usersService.updateUsers(usersId, request));
    }

    @DeleteMapping("/users/{usersId}")
    public void deleteUsers(@PathVariable Long usersId) {
        usersService.deleteUsers(usersId);
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        try {
            usersService.login(request, httpServletRequest);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }
}