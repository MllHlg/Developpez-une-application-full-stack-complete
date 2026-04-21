import { Component, inject } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { MatDivider } from "@angular/material/divider";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from "@angular/material/input";
import { Bouton } from "../../shared/components/bouton/bouton";
import { UserService } from '../../core/services/user';
import { Observable } from 'rxjs';
import { User } from '../../core/models/user';
import { CommonModule } from '@angular/common';
import { ThemeCard } from "../../shared/components/theme-card/theme-card";

@Component({
  selector: 'app-me',
  imports: [NavBar, MatDivider, MatFormFieldModule, MatInput, Bouton, CommonModule, ThemeCard],
  templateUrl: './me.html',
  styleUrl: './me.scss',
})
export class Me {
  private userService = inject(UserService);
  public user$: Observable<User> = this.userService.user();
}
