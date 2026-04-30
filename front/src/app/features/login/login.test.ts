import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing'; 
import { Router } from '@angular/router';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Login } from './login';
import { AuthService } from '../../core/services/auth';
import { SessionService } from '../../core/services/session';

describe('Login Component', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;
  
  let mockAuthService: any;
  let mockSessionService: any;
  let router: Router;

  beforeEach(async () => {
    mockAuthService = {
      login: jest.fn()
    };
    mockSessionService = {
      logIn: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        Login, 
        ReactiveFormsModule,
        RouterTestingModule 
      ],
      providers: [
        provideNoopAnimations(),
        { provide: AuthService, useValue: mockAuthService },
        { provide: SessionService, useValue: mockSessionService }
      ]
    }).compileComponents();

    router = TestBed.inject(Router);
    jest.spyOn(router, 'navigate').mockImplementation(() => Promise.resolve(true));
    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to the Articles page on successful login', () => {
    const mockSessionInfo = { token: 'token', type: 'Bearer', id: 1, username: 'test_user', email: 'test@test.com' };
    mockAuthService.login.mockReturnValue(of(mockSessionInfo));

    component.form.controls['identifiant'].setValue('test_user');
    component.form.controls['password'].setValue('TEST_password_123');

    component.submit();

    expect(mockAuthService.login).toHaveBeenCalledWith({ identifiant: 'test_user', password: 'TEST_password_123' });
    expect(mockSessionService.logIn).toHaveBeenCalledWith(mockSessionInfo);
    expect(router.navigate).toHaveBeenCalledWith(['/articles']); 
  });

  it('should show an error string on failed login with status 401', () => {
    const errorResponse = new HttpErrorResponse({ error: 'Erreur string', status: 401 });
    mockAuthService.login.mockReturnValue(throwError(() => errorResponse));
    component.submit();
    expect(component.errorMessage).toBe('Erreur string');
  });

  it('should show an error message on failed login with status 500', () => {
    const errorResponse = new HttpErrorResponse({ error: null, status: 500 });
    mockAuthService.login.mockReturnValue(throwError(() => errorResponse));
    component.submit();
    expect(component.errorMessage).toBe('Une erreur est survenue');
  });
});