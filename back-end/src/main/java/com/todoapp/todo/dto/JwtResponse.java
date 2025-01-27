package com.todoapp.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String message;

    public JwtResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
