import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TodoWriterComponent} from './widgets/todo-writer/todo-writer.component';
import {TodoEntry} from '../../../../domain/todoEntry';

@Component({
  selector: 'app-create-todo-dialog',
  templateUrl: './create-todo-dialog.component.html',
  styleUrls: ['./create-todo-dialog.component.scss']
})
export class CreateTodoDialogComponent implements OnInit {

  form: FormGroup;
  @ViewChild(TodoWriterComponent) todoWriter: TodoWriterComponent;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<CreateTodoDialogComponent>) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  save(): void {
    if (this.form.valid) {
      const newTodo = {
        ...this.form.value,
        id: null,
        completed: false,
        createdAt: new Date()
      } as TodoEntry;
      console.table(newTodo);
      this.dialogRef.close(newTodo);
    }
  }

  close(): void {
    this.dialogRef.close();
  }
}
