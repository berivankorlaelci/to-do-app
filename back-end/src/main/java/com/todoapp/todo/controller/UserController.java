package com.todoapp.todo.controller;

import com.todoapp.todo.entity.User;
import com.todoapp.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user){
        System.out.println("Received User: " + user);
         return userService.saveUser(user);
    }

}
