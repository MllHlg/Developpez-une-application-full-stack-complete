import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { ArticleDetails } from './article-details';
import { ArticleService } from '../../../core/services/article';
import { ArticleDetail } from '../../../core/models/articleDetail';

describe('ArticleDetails Component', () => {
  let component: ArticleDetails;
  let fixture: ComponentFixture<ArticleDetails>;
  let mockArticleService: any;

  const mockArticleDetail: ArticleDetail = {
    id: 1,
    titre: 'Article 1',
    date: new Date('2026-04-29'),
    auteur: 'user_1',
    texte: 'Contenu de l\'article 1',
    theme: 'Thème 1',
    commentaires: [
      { id: 1, comment: 'Commentaire', auteur: 'user_2' }
    ]
  };

  beforeEach(async () => {
    mockArticleService = {
      detail: jest.fn().mockReturnValue(of(mockArticleDetail)),
      comment: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        ArticleDetails, 
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [
        provideNoopAnimations(),
        { provide: ArticleService, useValue: mockArticleService },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: () => '1' } }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ArticleDetails);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should send a comment and refresh article details', () => {
    mockArticleService.comment.mockReturnValue(of(undefined));
    component.form.controls['comment'].setValue('Commentaire test');

    component.post();

    expect(mockArticleService.comment).toHaveBeenCalledWith('1', { comment: 'Commentaire test' });
    expect(mockArticleService.detail).toHaveBeenCalledTimes(2);
    expect(component.form.value.comment).toBeNull();
  });

  it('should show an error string on failed comment post with status 400', () => {
    const errorResponse = new HttpErrorResponse({ error: 'Erreur string', status: 400 });
    mockArticleService.comment.mockReturnValue(throwError(() => errorResponse));
    component.post();
    expect(component.errorMessage).toBe('Erreur string');
  });

  it('should show an error message on failed comment post with status 500', () => {
    const errorResponse = new HttpErrorResponse({ error: null, status: 500 });
    mockArticleService.comment.mockReturnValue(throwError(() => errorResponse));
    component.post();
    expect(component.errorMessage).toBe('Une erreur est survenue');
  });
});