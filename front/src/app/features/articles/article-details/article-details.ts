import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { MatIcon } from '@angular/material/icon';
import { ArticleService } from '../../../core/services/article';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { ArticleDetail } from '../../../core/models/articleDetail';

@Component({
  selector: 'app-article-details',
  imports: [RouterLink, NavBar, MatDivider, MatIcon, CommonModule, MatFormFieldModule, MatInput],
  templateUrl: './article-details.html',
  styleUrl: './article-details.scss',
})
export class ArticleDetails implements OnInit {

  public article$!: Observable<ArticleDetail>;

  constructor(private route: ActivatedRoute, private articleService: ArticleService) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')!
    this.article$ = this.articleService.detail(id);
  }
}
