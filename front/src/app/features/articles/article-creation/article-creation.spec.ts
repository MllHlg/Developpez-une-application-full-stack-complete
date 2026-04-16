import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleCreation } from './article-creation';

describe('ArticleCreation', () => {
  let component: ArticleCreation;
  let fixture: ComponentFixture<ArticleCreation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleCreation],
    }).compileComponents();

    fixture = TestBed.createComponent(ArticleCreation);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
