package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;


    public Todo(String title, String content, Users users) {
        this.title = title;
        this.content = content;
        this.users = users;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
