package com.example.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequest {
    private String title;
    private String content;
}
