import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TodoWriterComponent} from '../create-todo-dialog/widgets/todo-writer/todo-writer.component';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TodoEntry} from '../../../../domain/todoEntry';
import {TodoModelState} from '../todo-list/todo-list.model';

@Component({
  selector: 'app-edit-todo-dialog',
  templateUrl: './edit-todo-dialog.component.html',
  styleUrls: ['./edit-todo-dialog.component.scss']
})
export class EditTodoDialogComponent implements OnInit {

  form: FormGroup;
  @ViewChild(TodoWriterComponent) todoWriter: TodoWriterComponent;

  constructor(private fb: FormBuilder,
              private dialogRef: MatDialogRef<EditTodoDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: TodoModelState) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      title: [this.data.title, Validators.required],
      description: [this.data.description, Validators.required],
    });
  }

  save(): void {
    if (this.form.valid) {
      const editedTodo = {
        ...this.data,
        ...this.form.value,
      } as TodoEntry;
      this.dialogRef.close(editedTodo);
    }
  }

  close(): void {
    this.dialogRef.close();
  }
}
