import { CommonModule } from '@angular/common';
import { Component, DestroyRef, inject, Input } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { SessionService } from '../../../core/services/session';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-nav-bar',
  imports: [RouterLink, RouterLinkActive, MatIcon, MatMenuModule, CommonModule],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.scss',
})
export class NavBar {
  @Input() fullInfos!: boolean;
  private sessionService = inject(SessionService);
  private destroyRef = inject(DestroyRef);

  public isMenuOpen = false;

  public logout() {
    this.sessionService.logOut();
  }
}
