package com.todoapp.todo.controller;

import com.todoapp.todo.dto.AuthRequest;
import com.todoapp.todo.dto.CreateUserRequest;
import com.todoapp.todo.dto.JwtResponse;
import com.todoapp.todo.entity.User;
import com.todoapp.todo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/getUsers")
    public List<User> getUsers() { return userService.getAllUsers(); }

    @PostMapping("/register")
    public User saveUser(@RequestBody CreateUserRequest user){ return userService.createUser(user); }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody AuthRequest user){ return userService.login(user); }

}
