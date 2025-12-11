import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiungiStruttura } from './aggiungi-struttura';

describe('AggiungiStruttura', () => {
  let component: AggiungiStruttura;
  let fixture: ComponentFixture<AggiungiStruttura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AggiungiStruttura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiungiStruttura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
