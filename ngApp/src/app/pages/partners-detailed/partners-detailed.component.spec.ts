import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PartnersDetailedComponent } from './partners-detailed.component';

describe('PartnersDetailedComponent', () => {
  let component: PartnersDetailedComponent;
  let fixture: ComponentFixture<PartnersDetailedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PartnersDetailedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PartnersDetailedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
