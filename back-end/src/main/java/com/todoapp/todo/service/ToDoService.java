package com.todoapp.todo.service;

import com.todoapp.todo.entity.ToDo;
import com.todoapp.todo.repository.ToDoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService{
    private final ToDoRepository toDoRepository;

    ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository=toDoRepository;
    }

    public List<ToDo> getAll(){
        return toDoRepository.findAll();
    }

    public Optional<ToDo> getToDo(Long id){ return toDoRepository.findById(id);}

    public ToDo saveToDo(ToDo toDo){
         if(toDo.getId()==null){toDo.setCreatedAt(LocalDateTime.now());}
        toDo.setUpdatedAt(LocalDateTime.now());
        return toDoRepository.save(toDo);
    }

    public void deleteToDo(Long id){toDoRepository.deleteById(id);}
}
