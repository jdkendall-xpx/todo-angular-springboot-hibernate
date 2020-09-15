import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-toggle-checkbox',
  templateUrl: './toggle-checkbox.component.html',
  styleUrls: ['./toggle-checkbox.component.scss']
})
export class ToggleCheckboxComponent implements OnInit {
  @Input() state: boolean;
  @Input() pending: boolean;
  @Output() toggle = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  onToggle(): void {
    this.toggle.emit();
  }
}
