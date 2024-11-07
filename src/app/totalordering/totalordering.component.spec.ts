import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalorderingComponent } from './totalordering.component';

describe('TotalorderingComponent', () => {
  let component: TotalorderingComponent;
  let fixture: ComponentFixture<TotalorderingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TotalorderingComponent]
    });
    fixture = TestBed.createComponent(TotalorderingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
