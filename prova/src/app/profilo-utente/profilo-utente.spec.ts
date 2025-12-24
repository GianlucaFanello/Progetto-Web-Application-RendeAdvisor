import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfiloUtente } from './profilo-utente';

describe('ProfiloUtente', () => {
  let component: ProfiloUtente;
  let fixture: ComponentFixture<ProfiloUtente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfiloUtente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfiloUtente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
