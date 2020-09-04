import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TodoWriterComponent} from './todo-writer.component';

describe('TodoWriterComponent', () => {
  let component: TodoWriterComponent;
  let fixture: ComponentFixture<TodoWriterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TodoWriterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TodoWriterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
