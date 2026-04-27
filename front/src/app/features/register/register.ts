import { Component, DestroyRef, inject } from '@angular/core';
import { NavBar } from '../../shared/components/nav-bar/nav-bar';
import { MatIcon } from '@angular/material/icon';
import { Bouton } from '../../shared/components/bouton/bouton';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatInput } from '@angular/material/input';
import { MatDivider } from '@angular/material/divider';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth';
import { passwordValidator } from '../../shared/validators/password';
import { RegisterRequest } from '../../core/models/userFormRequest';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  imports: [NavBar, MatIcon, Bouton, MatFormField, MatLabel, MatInput, MatDivider, RouterLink, ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {
  private authServie = inject(AuthService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);
  private matSnackBar = inject(MatSnackBar);

  public errorMessage: string | null = null;

  public form = this.fb.group({
    username: [
      '',
      [Validators.required]
    ],
    email: [
      '',
      [Validators.required,
      Validators.email]
    ],
    password: [
      '',
      [Validators.required,
      Validators.minLength(8),
      passwordValidator]
    ]
  });

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.errorMessage = null;
    this.authServie.register(registerRequest)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (_: void) => {
          this.router.navigate(['/login']);
          this.matSnackBar.open('Votre compte a bien été créé !', 'Fermer', {duration: 3000});
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message || err.error || "Une erreur est survenue";
        }
      });
  }
}
