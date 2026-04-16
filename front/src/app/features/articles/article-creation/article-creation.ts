import { Component, inject } from '@angular/core';
import { NavBar } from '../../../shared/components/nav-bar/nav-bar';
import { MatDivider } from '@angular/material/divider';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Bouton } from '../../../shared/components/bouton/bouton';
import { MatInput } from '@angular/material/input';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { ThemeService } from '../../../core/services/theme';
import { Observable } from 'rxjs';
import { Theme } from '../../../core/models/theme';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-article-creation',
  imports: [RouterLink, NavBar, MatDivider, MatIcon, MatFormFieldModule, Bouton, MatInput, MatSelectModule, CommonModule],
  templateUrl: './article-creation.html',
  styleUrl: './article-creation.scss',
})
export class ArticleCreation {
  private themeService = inject(ThemeService);

  public themes$: Observable<Theme[]> = this.themeService.all();
}
