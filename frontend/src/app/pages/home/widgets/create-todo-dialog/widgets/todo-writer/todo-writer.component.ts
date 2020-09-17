import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-todo-writer',
  templateUrl: './todo-writer.component.html',
  styleUrls: ['./todo-writer.component.scss']
})
export class TodoWriterComponent implements OnInit {

  @Input()
  formGroup: FormGroup;

  constructor() {
  }

  ngOnInit(): void {
  }

}
