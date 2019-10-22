import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicablespheresComponent } from './applicablespheres.component';

describe('ApplicablespheresComponent', () => {
  let component: ApplicablespheresComponent;
  let fixture: ComponentFixture<ApplicablespheresComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplicablespheresComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplicablespheresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
