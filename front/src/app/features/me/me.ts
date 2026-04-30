import { Component, DestroyRef, inject } from '@angular/core';
import { NavBar } from "../../shared/components/nav-bar/nav-bar";
import { MatDivider } from "@angular/material/divider";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatInput } from "@angular/material/input";
import { Bouton } from "../../shared/components/bouton/bouton";
import { UserService } from '../../core/services/user';
import { Observable, tap } from 'rxjs';
import { User } from '../../core/models/user';
import { CommonModule } from '@angular/common';
import { ThemeCard } from "../../shared/components/theme-card/theme-card";
import { ThemeService } from '../../core/services/theme';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { UserFormRequest } from '../../core/models/userFormRequest';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { passwordValidator } from '../../shared/validators/password';
import { SessionService } from '../../core/services/session';
import { SessionInformations } from '../../core/models/sessionInformations';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-me',
  imports: [NavBar, MatDivider, MatFormFieldModule, MatInput, Bouton, CommonModule, ThemeCard, ReactiveFormsModule],
  templateUrl: './me.html',
  styleUrl: './me.scss',
})
export class Me {
  private userService = inject(UserService);
  private themeService = inject(ThemeService);
  private sessionService = inject(SessionService);
  private fb = inject(FormBuilder);
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
      [Validators.minLength(8),
      passwordValidator]
    ]
  });

  public user$: Observable<User> = this.userService.user().pipe(
    tap(user => {
      this.form.patchValue({
        username: user.username,
        email: user.email
      });
    })
  );

  public desabonnement(id: number): void {
    this.themeService.desabonnement(id)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.user$ = this.userService.user()
      });
  }

  public modifier(): void {
    const user = this.form.value as UserFormRequest;
    this.errorMessage = null;
    this.userService.update(user)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (response : SessionInformations) => {
          this.sessionService.logIn(response);
          this.user$ = this.userService.user();
          this.matSnackBar.open("Vos informations ont bien été modifiées !", 'Fermer', {duration: 3000});
        },
        error: (err: HttpErrorResponse) => {
          this.errorMessage = err.error?.message || err.error || "Une erreur est survenue";
        }
      })
  }
}
