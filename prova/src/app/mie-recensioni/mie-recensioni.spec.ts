import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MieRecensioni } from './mie-recensioni';

describe('MieRecensioni', () => {
  let component: MieRecensioni;
  let fixture: ComponentFixture<MieRecensioni>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MieRecensioni]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MieRecensioni);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
