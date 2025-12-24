import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NelCuore } from './nel-cuore';

describe('NelCuore', () => {
  let component: NelCuore;
  let fixture: ComponentFixture<NelCuore>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NelCuore]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NelCuore);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
