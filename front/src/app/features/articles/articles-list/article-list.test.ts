import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of, throwError } from 'rxjs';
import { ArticlesList } from './articles-list';
import { ArticleService } from '../../../core/services/article';
import { Article } from '../../../core/models/article';

describe('ArticlesListComponent', () => {
  let component: ArticlesList;
  let fixture: ComponentFixture<ArticlesList>;
  let mockArticleService: any;

  const mockArticles: Article[] = [
    { id: 1, titre: 'Article 1', date: new Date('2026-04-29'), auteur: 'user_1', texte: 'Contenu article 1' },
    { id: 2, titre: 'Article 2', date: new Date('2026-04-30'), auteur: 'user_2', texte: 'Contenu article 2' }
  ];

  beforeEach(async () => {
    mockArticleService = {
      all: jest.fn().mockReturnValue(of(mockArticles))
    };

    await TestBed.configureTestingModule({
      imports: [
        ArticlesList,
        RouterTestingModule
      ],
      providers: [
        { provide: ArticleService, useValue: mockArticleService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ArticlesList);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should get articles on init and sort them', () => {
    fixture.detectChanges();

    expect(mockArticleService.all).toHaveBeenCalled();
    expect(component.isLoading).toBe(false);
    expect(component.articles.length).toBe(2);
    expect(component.articles[0].titre).toBe('Article 1'); 
  });

  it('should sort the articles list', () => {
    fixture.detectChanges();
    expect(component.articles[0].titre).toBe('Article 1');

    component.sort();

    expect(component.articles[0].titre).toBe('Article 2');
    expect(component.triDesc).toBe(true);
  });
});