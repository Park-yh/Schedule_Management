package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String username;
    private String password;


    public Todo(String title, String content, String username, String password) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.password = password;
    }

    public void updateTodo(String title, String author) {
        this.title = title;
        this.username = author;
    }
}
