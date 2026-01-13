import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RispostePage } from './risposte-page';

describe('RispostePage', () => {
  let component: RispostePage;
  let fixture: ComponentFixture<RispostePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RispostePage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RispostePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
