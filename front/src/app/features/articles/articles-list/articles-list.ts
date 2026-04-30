import { ChangeDetectorRef, Component, DestroyRef, inject, OnDestroy, OnInit } from '@angular/core';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { Bouton } from "../../../shared/components/bouton/bouton";
import { ArticleService } from '../../../core/services/article';
import { Observable } from 'rxjs';
import { Article } from '../../../core/models/article';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ArticleCard } from './article-card/article-card';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-articles-list',
  imports: [NavBar, MatDivider, ArticleCard, Bouton, CommonModule, MatIcon, RouterLink],
  templateUrl: './articles-list.html',
  styleUrl: './articles-list.scss',
})
export class ArticlesList implements OnInit {
  private articleService = inject(ArticleService);
  private destroyRef = inject(DestroyRef);
  private cdr = inject(ChangeDetectorRef);
  public articles$: Observable<Article[]> = this.articleService.all();
  public articles: Article[] = [];
  public isLoading: boolean = true;

  public triDesc = true;

  ngOnInit(): void {
    this.articles$
      .pipe(
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe({
        next: (data) => {
          this.articles = data;
          this.sort();
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (_) => {
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
  }

  public sort(): void {
    this.articles.sort((a: Article, b: Article) => {
      const timeA = new Date(a.date).getTime();
      const timeB = new Date(b.date).getTime();
      return this.triDesc ? (timeA - timeB) : (timeB - timeA);
    });
    this.triDesc = !this.triDesc;
  }
}
