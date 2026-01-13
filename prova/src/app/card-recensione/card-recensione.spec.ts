import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardRecensione } from './card-recensione';

describe('CardRecensione', () => {
  let component: CardRecensione;
  let fixture: ComponentFixture<CardRecensione>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardRecensione]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardRecensione);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
