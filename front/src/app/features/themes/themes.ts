import { Component, DestroyRef, inject } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { ThemeService } from '../../core/services/theme';
import { Observable } from 'rxjs';
import { Theme } from '../../core/models/theme';
import { CommonModule } from '@angular/common';
import { MatDivider } from '@angular/material/divider';
import { ThemeCard } from '../../shared/components/theme-card/theme-card';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-themes-list',
  imports: [NavBar, ThemeCard, CommonModule, MatDivider],
  templateUrl: './themes.html',
  styleUrl: './themes.scss',
})
export class ThemesList {
  private themeService = inject(ThemeService);
  private destroyRef = inject(DestroyRef);

  public themes$: Observable<Theme[]> = this.themeService.all(); 

  public abonnement(id: number): void {
    this.themeService.abonnement(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.themes$ = this.themeService.all(),
      });
  }
}
