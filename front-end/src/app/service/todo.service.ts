import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ToDo } from '../model/todo';

@Injectable({
  providedIn: 'root',
})
export class ToDoService {
  private apiUrl = 'http://localhost:8080/api/ToDo'; // Backend API base URL

  constructor(private http: HttpClient) {}

  getTasks(): Observable<ToDo[]> {
    return this.http.get<ToDo[]>(`${this.apiUrl}/get`);
  }

  getToDo(id: number): Observable<ToDo> {
    return this.http.get<ToDo>(`${this.apiUrl}/get/${id}`);
  }

  saveToDo(toDo: ToDo): Observable<ToDo> {
    return this.http.post<ToDo>(`${this.apiUrl}/save`, toDo);
  }

  deleteToDo(id?: number ): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
}
