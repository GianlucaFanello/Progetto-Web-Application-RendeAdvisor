import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RisultatiR } from './risultati-r';

describe('RisultatiR', () => {
  let component: RisultatiR;
  let fixture: ComponentFixture<RisultatiR>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RisultatiR]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RisultatiR);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
