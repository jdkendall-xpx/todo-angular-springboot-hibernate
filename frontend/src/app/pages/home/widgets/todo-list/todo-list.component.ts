import {Component, Input, OnInit} from '@angular/core';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoListModel, TodoModelState} from './todo-list.model';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {EditTodoDialogComponent} from '../edit-todo-dialog/edit-todo-dialog.component';

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.scss']
})
export class TodoListComponent implements OnInit {

  @Input()
  model: TodoListModel;

  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  onToggleTodoCompletedState(todo: TodoEntry): void {
    // Update our to-do entry with toggled completion state
    this.model.toggleTodoCompletedState(todo);
  }

  onEditCard(entry: TodoModelState): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    if (window.innerWidth <= 500) {
      dialogConfig.width = '90vw';
    }

    dialogConfig.data = entry;

    this.dialog.open(EditTodoDialogComponent, dialogConfig)
      .afterClosed()
      .subscribe(n => this.onEditTodoDialogClose(n));
  }

  private onEditTodoDialogClose(result: TodoEntry): void {
    if (result) {
      this.model.updateTodoEntry(result);
    }
  }
}
