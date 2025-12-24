import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfiloStruttura } from './profilo-struttura';

describe('ProfiloStruttura', () => {
  let component: ProfiloStruttura;
  let fixture: ComponentFixture<ProfiloStruttura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfiloStruttura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfiloStruttura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
