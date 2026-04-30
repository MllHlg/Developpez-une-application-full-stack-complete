import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ArticleCreation } from './article-creation';
import { ArticleService } from '../../../core/services/article';
import { ThemeService } from '../../../core/services/theme';
import { Theme } from '../../../core/models/theme';

describe('ArticleCreation Component', () => {
  let component: ArticleCreation;
  let fixture: ComponentFixture<ArticleCreation>;
  let mockArticleService: any;
  let mockThemeService: any;
  let mockSnackBar: any;

  const mockThemes: Theme[] = [
    { id: 1, titre: 'Thème 1', description: 'Description du thème 1', abonnement: false },
    { id: 2, titre: 'Thème 2', description: 'Description du thème 2', abonnement: true }
  ];

  beforeEach(async () => {
    mockArticleService = {
      create: jest.fn()
    };
    mockThemeService = {
      all: jest.fn().mockReturnValue(of(mockThemes))
    };
    mockSnackBar = {
      open: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        ArticleCreation, 
        ReactiveFormsModule, 
        RouterTestingModule
      ],
      providers: [
        provideNoopAnimations(),
        { provide: ArticleService, useValue: mockArticleService },
        { provide: ThemeService, useValue: mockThemeService },
        { provide: MatSnackBar, useValue: mockSnackBar }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ArticleCreation);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should show a message and reset form on successful creation', () => {
    mockArticleService.create.mockReturnValue(of(undefined));
    component.form.controls['titre'].setValue('Article 3');
    component.form.controls['theme'].setValue(1);
    component.form.controls['texte'].setValue('Contenu article 3');

    component.create();

    expect(mockArticleService.create).toHaveBeenCalledWith({
      titre: 'Article 3',
      theme: 1,
      texte: 'Contenu article 3'
    });
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      "L'article a bien été créé !", 
      'Fermer', 
      { duration: 3000 }
    );
    expect(component.form.value.titre).toBeNull();
    expect(component.form.value.texte).toBeNull();
  });

  it('should show an error message on failed creation with status 400', () => {
    const errorResponse = new HttpErrorResponse({ error: 'Erreur string', status: 400 });
    mockArticleService.create.mockReturnValue(throwError(() => errorResponse));
    component.create();
    expect(component.errorMessage).toBe('Erreur string');
  });

  it('should show an error message on failed creation with status 500', () => {
    const errorResponse = new HttpErrorResponse({ error: null, status: 500 });
    mockArticleService.create.mockReturnValue(throwError(() => errorResponse));
    component.create();
    expect(component.errorMessage).toBe('Une erreur est survenue');
  });
});