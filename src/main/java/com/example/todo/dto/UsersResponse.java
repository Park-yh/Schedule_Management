package com.example.todo.dto;

import com.example.todo.entity.Users;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UsersResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UsersResponse(Users users){
        this.id = users.getId();
        this.name = users.getName();
        this.email = users.getEmail();
        this.createdAt = users.getCreatedAt();
        this.modifiedAt = users.getModifiedAt();
    }
}
