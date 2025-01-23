import { Component, OnInit } from '@angular/core';
import { ToDoService } from '../service/todo.service';
import { ToDo } from '../model/todo';

@Component({
  selector: 'app-todo',
  templateUrl: './todo.component.html',
  styleUrls: ['./todo.component.css'],
  standalone: false
})
export class ToDoComponent implements OnInit {
  todos: ToDo[] = [];
  newToDo: ToDo = new ToDo();

  constructor(private toDoService: ToDoService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.toDoService.getTasks().subscribe((data: ToDo[]) => {
      this.todos = data;
    });
  }

  saveTask(): void {
    if (this.newToDo.title && this.newToDo.description) {
      this.toDoService.saveToDo(this.newToDo).subscribe(() => {
        this.loadTasks();
        this.newToDo = new ToDo();
      });
    } else {
      alert('Title and Description are required!');
    }
  }

  selectToDoForEdit(todo: ToDo): void {
    this.newToDo = { ...todo };
  }

  deleteTask(id?: number): void {
    this.toDoService.deleteToDo(id).subscribe(() => {
      this.todos = this.todos.filter((todo) => todo.id !== id);
    });
  }
}
