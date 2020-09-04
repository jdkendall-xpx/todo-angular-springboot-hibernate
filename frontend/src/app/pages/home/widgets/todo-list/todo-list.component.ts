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
    // Load the todo list for the first time
    this.updateTodoList();
  }

  private updateTodoList(): void {
    // Ask the API for the todo list,
    // then publish it to our subject
    this.todoApiService.getTodoList()
      .then(result => this.$todos.next(result));
  }

  onToggleCompleted(todo: TodoEntry): void {
    // Update our Todo entry with toggled completion state
    // Then trigger a refresh of the todo list
    this.todoApiService.updateTodo({...todo, completed: !todo.completed})
      .then(_ => this.updateTodoList());
  }
}
