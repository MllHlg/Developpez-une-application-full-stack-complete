import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Me } from './me';
import { UserService } from '../../core/services/user';
import { ThemeService } from '../../core/services/theme';
import { SessionService } from '../../core/services/session';
import { User } from '../../core/models/user';
import { SessionInformations } from '../../core/models/sessionInformations';

describe('Me Component', () => {
  let component: Me;
  let fixture: ComponentFixture<Me>;
  let mockUserService: any;
  let mockThemeService: any;
  let mockSessionService: any;
  let mockSnackBar: any;

  const mockUser: User = {
    id: 1,
    username: 'test_user',
    email: 'test@test.com',
    themes: [
      { id: 1, titre: 'Thème 1', description: 'Description du thème 1', abonnement: true },
      { id: 2, titre: 'Thème 2', description: 'Description du thème 2', abonnement: true }
    ]
  };

  beforeEach(async () => {
    mockUserService = {
      user: jest.fn().mockReturnValue(of(mockUser)), 
      update: jest.fn()
    };
    
    mockThemeService = {
      desabonnement: jest.fn().mockReturnValue(of({ message: 'Désabonné' }))
    };

    mockSessionService = {
      logIn: jest.fn()
    };

    mockSnackBar = {
      open: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        Me, 
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [
        provideNoopAnimations(),
        { provide: UserService, useValue: mockUserService },
        { provide: ThemeService, useValue: mockThemeService },
        { provide: SessionService, useValue: mockSessionService },
        { provide: MatSnackBar, useValue: mockSnackBar }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(Me);
    component = fixture.componentInstance;
    fixture.detectChanges(); 
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should refresh user data on unsubscribe', () => {
    component.desabonnement(1);

    expect(mockThemeService.desabonnement).toHaveBeenCalledWith(1);
    expect(mockUserService.user).toHaveBeenCalledTimes(2); 
  });

  it('should show an error string on failed modification with status 400', () => {
    const errorResponse = new HttpErrorResponse({ error: 'Erreur string', status: 400 });
    mockUserService.update.mockReturnValue(throwError(() => errorResponse));
    component.modifier();
    expect(component.errorMessage).toBe('Erreur string');
  });

  it('should show an error message on failed modification with status 500', () => {
    const errorResponse = new HttpErrorResponse({ error: null, status: 500 });
    mockUserService.update.mockReturnValue(throwError(() => errorResponse));
    component.modifier();
    expect(component.errorMessage).toBe('Une erreur est survenue');
  });
});