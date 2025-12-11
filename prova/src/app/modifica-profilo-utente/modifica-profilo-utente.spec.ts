import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaProfiloUtente } from './modifica-profilo-utente';

describe('ModificaProfiloUtente', () => {
  let component: ModificaProfiloUtente;
  let fixture: ComponentFixture<ModificaProfiloUtente>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModificaProfiloUtente]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificaProfiloUtente);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
