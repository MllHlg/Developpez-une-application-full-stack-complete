import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemesList } from './themes-list';

describe('ThemesList', () => {
  let component: ThemesList;
  let fixture: ComponentFixture<ThemesList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThemesList],
    }).compileComponents();

    fixture = TestBed.createComponent(ThemesList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
