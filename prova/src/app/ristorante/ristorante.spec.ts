import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ristorante } from './ristorante';

describe('Ristorante', () => {
  let component: Ristorante;
  let fixture: ComponentFixture<Ristorante>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Ristorante]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ristorante);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
