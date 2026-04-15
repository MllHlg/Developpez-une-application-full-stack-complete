import { Component } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { MatIcon } from '@angular/material/icon';
import { Bouton } from "../../shared/components/bouton/bouton";
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatDivider } from '@angular/material/divider';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-login',
  imports: [NavBar, MatIcon, Bouton, MatFormField, MatLabel, MatInput, MatDivider, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
}
