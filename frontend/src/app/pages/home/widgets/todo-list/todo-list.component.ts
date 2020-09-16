import {Component, Input, OnInit} from '@angular/core';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoListModel, TodoModelState} from './todo-list.model';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  @Input()
  model: TodoListModel;

  constructor() {
  }

  ngOnInit(): void {
  }

  onToggleTodoCompletedState(todo: TodoEntry): void {
    // Update our to-do entry with toggled completion state
    this.model.toggleTodoCompletedState(todo);
  }

  onEditCard(_: TodoModelState): void {
    // TODO
  }
}
