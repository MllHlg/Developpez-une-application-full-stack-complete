import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ArticleService } from './article';
import { Article } from '../models/article';
import { ArticleDetail } from '../models/articleDetail';
import { ArticleCreate } from '../models/articleCreate';
import { CommentaireContent } from '../models/commentaireContent';

describe('ArticleService', () => {
  let service: ArticleService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ArticleService]
    });
    service = TestBed.inject(ArticleService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all articles', () => {
    const mockArticles: Article[] = [
      { id: 1, titre: 'Article 1', date: new Date('2026-04-29'), auteur: 'user_1', texte: 'Contenu article 1' },
      { id: 2, titre: 'Article 2', date: new Date('2026-04-30'), auteur: 'user_2', texte: 'Contenu article 2' }
    ];

    service.all().subscribe(articles => {
      expect(articles).toEqual(mockArticles);
      expect(articles.length).toBe(2);
    });

    const req = httpTestingController.expectOne('articles');
    expect(req.request.method).toBe('GET');
    req.flush(mockArticles);
  });

  it('should get an article details', () => {
    const articleId = '1';
    const mockArticleDetail: ArticleDetail = {
      id: 1,
      titre: 'Article 1',
      date: new Date('2026-04-29'),
      auteur: 'user_1',
      texte: 'Contenu article 1',
      theme: 'Thème 1',
      commentaires: [
        { id: 1, comment: 'Commentaire', auteur: 'user_2' }
      ]
    };

    service.detail(articleId).subscribe(detail => {
      expect(detail).toEqual(mockArticleDetail);
    });

    const req = httpTestingController.expectOne(`articles/${articleId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockArticleDetail);
  });

  it('should post a comment to an article', () => {
    const articleId = '1';
    const mockComment: CommentaireContent = {
      comment: 'Commentaire test'
    };

    service.comment(articleId, mockComment).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpTestingController.expectOne(`articles/${articleId}/commentaires`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockComment);
    req.flush(null);
  });

  it('should create a new article', () => {
    const mockArticleCreate: ArticleCreate = {
      titre: 'Article 3',
      theme: 1,
      texte: 'Contenu article 3'
    };

    service.create(mockArticleCreate).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpTestingController.expectOne('articles');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockArticleCreate);
    req.flush(null);
  });
});