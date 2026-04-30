import { Component, DestroyRef, inject } from '@angular/core';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Bouton } from '../../../shared/components/bouton/bouton';
import { MatInput } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ThemeService } from '../../../core/services/theme';
import { Observable } from 'rxjs';
import { Theme } from '../../../core/models/theme';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ArticleCreate } from '../../../core/models/articleCreate';
import { ArticleService } from '../../../core/services/article';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-article-creation',
  imports: [RouterLink, NavBar, MatDivider, MatIcon, MatFormFieldModule, Bouton, MatInput, MatSelectModule, CommonModule, ReactiveFormsModule],
  templateUrl: './article-creation.html',
  styleUrl: './article-creation.scss',
})
export class ArticleCreation {
  private themeService = inject(ThemeService);
  private articleService = inject(ArticleService);
  private fb = inject(FormBuilder);
  private destroyRef = inject(DestroyRef)
  private matSnackBar = inject(MatSnackBar);
  public errorMessage: string | null = null;

  public themes$: Observable<Theme[]> = this.themeService.all();

  public form = this.fb.group({
    titre: [
      '',
      [Validators.required,
      Validators.maxLength(50),
      ]
    ],
    theme: [
      null as number | null,
      [Validators.required]
    ],
    texte: [
      '',
      [Validators.required,
      Validators.maxLength(2500),
      ]
    ],
  })

  public create(): void {
    const article = this.form.value as ArticleCreate;
    this.errorMessage = null;
    this.articleService.create(article)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (_: void) => {
          this.form.reset();
          this.matSnackBar.open("L'article a bien été créé !", 'Fermer', {duration: 3000});
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message || err.error || "Une erreur est survenue";
        },
      });
  }
}
