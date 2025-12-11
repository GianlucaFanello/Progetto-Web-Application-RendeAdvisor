import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecensioniStruttura } from './recensioni-struttura';

describe('RecensioniStruttura', () => {
  let component: RecensioniStruttura;
  let fixture: ComponentFixture<RecensioniStruttura>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecensioniStruttura]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecensioniStruttura);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
