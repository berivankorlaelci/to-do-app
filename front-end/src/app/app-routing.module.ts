import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ToDoComponent} from './todo/todo.component';

const routes: Routes = [
  {path: "", component: ToDoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
