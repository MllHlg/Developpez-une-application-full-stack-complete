import { Component, inject } from '@angular/core';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { ArticleCard } from "../../../shared/components/article-card/article-card";
import { Bouton } from "../../../shared/components/bouton/bouton";
import { ArticleService } from '../../../core/services/article';
import { Observable } from 'rxjs';
import { Article } from '../../../core/models/article';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-articles-list',
  imports: [NavBar, MatDivider, ArticleCard, Bouton, CommonModule],
  templateUrl: './articles-list.html',
  styleUrl: './articles-list.scss',
})
export class ArticlesList {
  private articleService = inject(ArticleService);

  public articles$: Observable<Article[]> = this.articleService.all(); 
}
