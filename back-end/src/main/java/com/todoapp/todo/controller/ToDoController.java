package com.todoapp.todo.controller;

import com.todoapp.todo.entity.ToDo;
import com.todoapp.todo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ToDo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/get")
    public List<ToDo> getTasks() {
        return toDoService.getAll();
    }

    @GetMapping("/get/{id}")
    public Optional<ToDo> getToDo(@PathVariable Long id) { return toDoService.getToDo(id); }

    @PostMapping(path = "/save")
    public ToDo saveToDo(@RequestBody ToDo toDo) {return toDoService.saveToDo(toDo); }

    @DeleteMapping("/delete/{id}")
    public void deleteToDo(@PathVariable Long id){ toDoService.deleteToDo(id); }
}
