import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopbarSoloHome } from './topbar-solo-home';

describe('TopbarSoloHome', () => {
  let component: TopbarSoloHome;
  let fixture: ComponentFixture<TopbarSoloHome>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopbarSoloHome]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopbarSoloHome);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
