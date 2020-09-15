import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TodoModelState} from '../todo-list.model';

@Component({
  selector: 'app-todo-card',
  templateUrl: './todo-card.component.html',
  styleUrls: ['./todo-card.component.scss']
})
export class TodoCardComponent implements OnInit {

  @Input() todo: TodoModelState;
  @Output() toggle = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onToggle(): void {
    this.toggle.emit();
  }
}
