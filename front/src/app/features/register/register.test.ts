import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Register } from './register';
import { AuthService } from '../../core/services/auth';

describe('Register Component', () => {
  let component: Register;
  let fixture: ComponentFixture<Register>;
  let mockAuthService: any;
  let mockSnackBar: any;
  let router: Router;

  beforeEach(async () => {
    mockAuthService = {
      register: jest.fn() 
    };
    mockSnackBar = {
      open: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        Register, 
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [
        provideNoopAnimations(),
        { provide: AuthService, useValue: mockAuthService },
        { provide: MatSnackBar, useValue: mockSnackBar }
      ]
    }).compileComponents();

    router = TestBed.inject(Router);
    jest.spyOn(router, 'navigate').mockImplementation(() => Promise.resolve(true));

    fixture = TestBed.createComponent(Register);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should navigate to login and show a message on successful registration', () => {
    mockAuthService.register.mockReturnValue(of(undefined));
    component.form.controls['username'].setValue('test_user');
    component.form.controls['email'].setValue('test@test.com');
    component.form.controls['password'].setValue('TEST_password_123');

    component.submit();

    expect(mockAuthService.register).toHaveBeenCalledWith({ 
      username: 'test_user', 
      email: 'test@test.com', 
      password: 'TEST_password_123' 
    });
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
    expect(mockSnackBar.open).toHaveBeenCalledWith(
      'Votre compte a bien été créé !', 
      'Fermer', 
      { duration: 3000 }
    );
  });

  it('should show an error string on failed registration with status 400', () => {
    const errorResponse = new HttpErrorResponse({ error: 'Erreur string', status: 400 });
    mockAuthService.register.mockReturnValue(throwError(() => errorResponse));
    component.submit();
    expect(component.errorMessage).toBe('Erreur string');
  });

  it('should show an error message on failed registration with status 500', () => {
    const errorResponse = new HttpErrorResponse({ error: null, status: 500 });
    mockAuthService.register.mockReturnValue(throwError(() => errorResponse));
    component.submit();
    expect(component.errorMessage).toBe('Une erreur est survenue');
  });
});