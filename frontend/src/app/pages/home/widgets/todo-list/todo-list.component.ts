import {Component, OnInit} from '@angular/core';
import {TodoApiService} from '../../../../services/todo-api.service';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoListModel} from './todo-list.model';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  model: TodoListModel;

  constructor(private todoApiService: TodoApiService) {
    this.model = new TodoListModel(this.todoApiService);
  }

  ngOnInit(): void {
    // Load the to-do list for the first time
    this.model.updateTodoList();
    this.model.$todos.subscribe(next => console.table(next[1]));
  }

  onToggleTodoCompletedState(todo: TodoEntry): void {
    // Update our to-do entry with toggled completion state
    this.model.toggleTodoCompletedState(todo);
  }
}
