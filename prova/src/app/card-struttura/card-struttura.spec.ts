import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardStruttura } from './card-struttura';

describe('CardStruttura', () => {
  let component: CardStruttura;
  let fixture: ComponentFixture<CardStruttura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardStruttura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardStruttura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
