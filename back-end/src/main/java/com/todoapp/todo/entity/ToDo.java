package com.todoapp.todo.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Table(name = "todo_table")
@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private String description;
    @Column()
    private Boolean completed=false;
    @Column()
    private String createdBy;
    @Column()
    private LocalDateTime createdAt;
    @Column()
    private String updatedBy;
    @Column()
    private LocalDateTime updatedAt;

    /*@PrePersist
    public void prePersist() {
        this.createdBy = "defaultUser";
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedBy = "defaultUser";
        this.updatedAt = LocalDateTime.now();
    }*/
}
