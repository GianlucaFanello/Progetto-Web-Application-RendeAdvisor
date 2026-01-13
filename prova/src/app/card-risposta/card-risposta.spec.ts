import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardRisposta } from './card-risposta';

describe('CardRisposta', () => {
  let component: CardRisposta;
  let fixture: ComponentFixture<CardRisposta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardRisposta]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardRisposta);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
