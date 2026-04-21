import { Component, DestroyRef, inject } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { MatIcon } from '@angular/material/icon';
import { Bouton } from "../../shared/components/bouton/bouton";
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatDivider } from '@angular/material/divider';
import { Router, RouterLink } from "@angular/router";
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { LoginRequest } from '../../core/models/loginRequest';
import { AuthService } from '../../core/services/auth';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AuthSuccess } from '../../core/models/authSuccess';
import { passwordValidator } from '../../shared/validators/password';

@Component({
  selector: 'app-login',
  imports: [NavBar, MatIcon, Bouton, MatFormField, MatLabel, MatInput, MatDivider, RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private authService = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);

  public onError = false;

  private destroyRef = inject(DestroyRef);

  public form = this.fb.group({
    identifiant: [
      '',
      [
        Validators.required
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        passwordValidator
      ]
    ]
  });

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (_ : AuthSuccess) => this.router.navigate(['/articles']),
        error: _ => this.onError = true,
      });
  }
}
