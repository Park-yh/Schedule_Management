package com.example.todo.controller;

import com.example.todo.dto.UsersRequest;
import com.example.todo.dto.UsersResponse;
import com.example.todo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/users")
    public ResponseEntity<UsersResponse> createUsers(@RequestBody UsersRequest request) {
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
}