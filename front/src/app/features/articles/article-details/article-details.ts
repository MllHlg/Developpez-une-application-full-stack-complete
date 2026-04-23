import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { MatIcon } from '@angular/material/icon';
import { ArticleService } from '../../../core/services/article';
import { Observable, takeUntil } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { ArticleDetail } from '../../../core/models/articleDetail';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { commentaireContent } from '../../../core/models/commentaireContent';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-article-details',
  imports: [RouterLink, NavBar, MatDivider, MatIcon, CommonModule, MatFormFieldModule, MatInput, ReactiveFormsModule],
  templateUrl: './article-details.html',
  styleUrl: './article-details.scss',
})
export class ArticleDetails implements OnInit {

  public article$!: Observable<ArticleDetail>;
  private fb = inject(FormBuilder);
  private id!: string;
  private destroyRef = inject(DestroyRef);
  public onError = false;

  public form = this.fb.group({
    comment: [
      '',
      [Validators.required]
    ]
  })

  constructor(private route: ActivatedRoute, private articleService: ArticleService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!
    this.article$ = this.articleService.detail(this.id);
  }

  public post(): void {
    if (this.form.invalid) {
      return;
    }

    const comment = this.form.value as commentaireContent;

    this.articleService.comment(this.id, comment)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (_: void) => {
          this.article$ = this.articleService.detail(this.id);
          this.form.reset(); 
        },
        error: (err) => {
          this.onError = true;
        },
      });
  }
}
