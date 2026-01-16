import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfiloStrutturaComponent } from './profilo-struttura';

describe('ProfiloStruttura', () => {
  let component: ProfiloStrutturaComponent;
  let fixture: ComponentFixture<ProfiloStrutturaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfiloStrutturaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfiloStrutturaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
