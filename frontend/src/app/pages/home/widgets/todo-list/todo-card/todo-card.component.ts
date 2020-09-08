import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TodoEntry} from '../../../../../domain/todoEntry';

@Component({
  selector: 'app-todo-card',
  templateUrl: './todo-card.component.html',
  styleUrls: ['./todo-card.component.css']
})
export class TodoCardComponent implements OnInit {

  @Input() todo: TodoEntry;
  @Output() toggle = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onToggle(): void {
    this.toggle.emit();
  }
}
