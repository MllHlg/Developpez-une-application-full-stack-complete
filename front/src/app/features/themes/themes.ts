import { Component, inject } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { ThemeCard } from "../../shared/components/theme-card/theme-card";
import { ThemeService } from '../../core/services/theme';
import { Observable } from 'rxjs';
import { Theme } from '../../core/models/theme';
import { CommonModule } from '@angular/common';
import { MatDivider } from '@angular/material/divider';

@Component({
  selector: 'app-themes-list',
  imports: [NavBar, ThemeCard, CommonModule, MatDivider],
  templateUrl: './themes.html',
  styleUrl: './themes.scss',
})
export class ThemesList {
  private themeService = inject(ThemeService);

  public themes$: Observable<Theme[]> = this.themeService.all(); 
}
