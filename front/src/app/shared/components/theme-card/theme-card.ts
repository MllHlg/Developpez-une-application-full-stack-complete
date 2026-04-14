import { Component, Input } from '@angular/core';
import { MatCard, MatCardActions, MatCardContent, MatCardHeader, MatCardTitle } from '@angular/material/card';
import { Bouton } from "../bouton/bouton";
import { Theme } from '../../../core/models/theme';

@Component({
  selector: 'app-theme-card',
  imports: [Bouton, MatCard, MatCardHeader, MatCardTitle, MatCardContent, MatCardActions],
  templateUrl: './theme-card.html',
  styleUrl: './theme-card.scss',
})
export class ThemeCard {
  @Input() theme!: Theme;
}
