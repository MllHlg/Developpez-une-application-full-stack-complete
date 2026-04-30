import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { authGuard } from './auth';
import { SessionService } from '../services/session';

describe('AuthGuard', () => {
  let mockRouter: any;
  let mockSessionService: any;

  beforeEach(() => {
    mockRouter = { navigate: jest.fn() };
    mockSessionService = { isLogged: false };

    TestBed.configureTestingModule({
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: SessionService, useValue: mockSessionService }
      ]
    });
  });

  it('should return true if user is logged in', () => {
    mockSessionService.isLogged = true;
    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));
    expect(result).toBe(true);
    expect(mockRouter.navigate).not.toHaveBeenCalled();
  });

  it('should navigate to login and return false if user is not logged in', () => {
    mockSessionService.isLogged = false;
    const result = TestBed.runInInjectionContext(() => authGuard({} as any, {} as any));
    expect(result).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['login']);
  });
});