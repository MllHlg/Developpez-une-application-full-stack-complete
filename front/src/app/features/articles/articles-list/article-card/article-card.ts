import { Component, Input } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle } from '@angular/material/card';
import { Article } from '../../../../core/models/article';

@Component({
  selector: 'app-article-card',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardSubtitle],
  templateUrl: './article-card.html',
  styleUrl: './article-card.scss',
})
export class ArticleCard {
  @Input() article!: Article;
}
