import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungiR } from './aggiungi-r';

describe('AggiungiR', () => {
  let component: AggiungiR;
  let fixture: ComponentFixture<AggiungiR>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AggiungiR]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiungiR);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
