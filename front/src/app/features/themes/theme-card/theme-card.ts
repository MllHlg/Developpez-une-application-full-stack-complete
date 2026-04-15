import { Component, Input } from '@angular/core';
import { MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { Theme } from '../../../core/models/theme';
import { Bouton } from '../../../shared/components/bouton/bouton';

@Component({
  selector: 'app-theme-card',
  imports: [Bouton, MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardActions],
  templateUrl: './theme-card.html',
  styleUrl: './theme-card.scss',
})
export class ThemeCard {
  @Input() theme!: Theme;
}
