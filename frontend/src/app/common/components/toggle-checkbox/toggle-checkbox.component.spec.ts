import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ToggleCheckboxComponent} from './toggle-checkbox.component';

describe('ToggleableCheckBoxComponent', () => {
  let component: ToggleCheckboxComponent;
  let fixture: ComponentFixture<ToggleCheckboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ToggleCheckboxComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToggleCheckboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
