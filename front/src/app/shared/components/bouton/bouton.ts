import { Component, Input } from '@angular/core';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-bouton',
  imports: [MatButton],
  templateUrl: './bouton.html',
  styleUrl: './bouton.scss',
})
export class Bouton {
  @Input() texte!: String;
}
