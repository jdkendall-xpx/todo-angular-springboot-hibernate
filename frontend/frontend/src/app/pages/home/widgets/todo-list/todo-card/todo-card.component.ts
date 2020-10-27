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
  @Output() edit = new EventEmitter();
  @Output() delete = new EventEmitter();
  @Output() retry = new EventEmitter();
  @Output() cancel = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onToggle(): void {
    this.toggle.emit();
  }

  onEdit(): void {
    this.edit.emit();
  }

  onDelete(): void {
    this.delete.emit();
  }

  onFailRetry(): void {
    this.retry.emit();
  }

  onFailDiscard(): void {
    this.cancel.emit();
  }
}
