package com.todoapp.todo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;


}
