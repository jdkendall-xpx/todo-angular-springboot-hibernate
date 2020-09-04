import {Component, OnInit} from '@angular/core';
import {TodoApiService} from '../../../../services/todo-api.service';
import {TodoEntry} from '../../../../domain/todoEntry';
import {BehaviorSubject} from 'rxjs';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  $todos: BehaviorSubject<TodoEntry[]> = new BehaviorSubject<TodoEntry[]>([]);

  constructor(private todoApiService: TodoApiService) {
  }

  ngOnInit(): void {
    // Ask the API for the todo list, then publish it to our subject
    this.todoApiService.getTodoList().then(result => this.$todos.next(result));
  }
}
