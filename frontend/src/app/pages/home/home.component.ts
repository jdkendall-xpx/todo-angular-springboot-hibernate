import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {CreateTodoDialogComponent} from './widgets/create-todo-dialog/create-todo-dialog.component';
import {TodoListModel} from './widgets/todo-list/todo-list.model';
import {TodoApiService} from '../../services/todo-api.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  model: TodoListModel;

  constructor(private dialog: MatDialog, private todoApiService: TodoApiService) {
    this.model = new TodoListModel(this.todoApiService);
  }

  ngOnInit(): void {
    // Load the to-do list for the first time
    this.model.updateTodoList();
  }

  openNewTodoDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    if (window.innerWidth <= 500) {
      dialogConfig.width = '90vw';
    }

    this.dialog.open(CreateTodoDialogComponent, dialogConfig);
  }
}
