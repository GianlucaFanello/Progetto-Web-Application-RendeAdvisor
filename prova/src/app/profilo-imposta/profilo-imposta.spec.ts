import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfiloImposta } from './profilo-imposta';

describe('ProfiloImposta', () => {
  let component: ProfiloImposta;
  let fixture: ComponentFixture<ProfiloImposta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProfiloImposta]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfiloImposta);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
