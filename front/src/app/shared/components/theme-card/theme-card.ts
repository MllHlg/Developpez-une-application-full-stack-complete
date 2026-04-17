import { Component, Input } from '@angular/core';
import { MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { Theme } from '../../../core/models/theme';
import { Bouton } from '../bouton/bouton';

@Component({
  selector: 'app-theme-card',
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardActions, Bouton],
  templateUrl: './theme-card.html',
  styleUrl: './theme-card.scss',
})
export class ThemeCard {
  @Input() theme!: Theme;
  @Input() buttonText!: string;
}
