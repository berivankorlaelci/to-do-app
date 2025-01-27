package com.todoapp.todo.dto;

public record AuthRequest (
        String username,
        String password
){
}
