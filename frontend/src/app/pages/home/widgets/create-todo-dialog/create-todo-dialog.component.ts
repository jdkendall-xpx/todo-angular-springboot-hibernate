import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-create-todo-dialog',
  templateUrl: './create-todo-dialog.component.html',
  styleUrls: ['./create-todo-dialog.component.scss']
})
export class CreateTodoDialogComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder, private dialogRef: MatDialogRef<CreateTodoDialogComponent>) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      title: [Validators.required],
      description: [Validators.required],
    });
  }

  save(): void {
    this.dialogRef.close(this.form.value);
  }

  close(): void {
    this.dialogRef.close();
  }
}
