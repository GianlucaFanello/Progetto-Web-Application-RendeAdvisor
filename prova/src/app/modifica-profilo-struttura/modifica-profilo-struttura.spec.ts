import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificaProfiloStruttura } from './modifica-profilo-struttura';

describe('ModificaProfiloStruttura', () => {
  let component: ModificaProfiloStruttura;
  let fixture: ComponentFixture<ModificaProfiloStruttura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModificaProfiloStruttura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificaProfiloStruttura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
