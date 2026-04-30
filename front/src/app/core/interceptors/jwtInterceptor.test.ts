import { TestBed } from '@angular/core/testing';
import { HttpRequest } from '@angular/common/http';
import { customJwtInterceptorFn } from './jwtInterceptor';
import { SessionService } from '../services/session';

describe('JwtInterceptor', () => {
  let mockSessionService: any;

  beforeEach(() => {
    mockSessionService = {
      isLogged: false,
      sessionInformations: null
    };

    TestBed.configureTestingModule({
      providers: [
        { provide: SessionService, useValue: mockSessionService }
      ]
    });
  });

  it('should not modify the request if user is not logged in', (done) => {
    const request = new HttpRequest('GET', 'api/test');
    
    const nextFn = (req: HttpRequest<any>) => {
      expect(req.headers.has('Authorization')).toBe(false);
      done();
      return {} as any;
    };

    TestBed.runInInjectionContext(() => {
      customJwtInterceptorFn(request, nextFn);
    });
  });

  it('should add authorization header if user is logged in', (done) => {
    mockSessionService.isLogged = true;
    mockSessionService.sessionInformations = { token: 'fake-jwt-token' };
    
    const request = new HttpRequest('GET', 'api/test');
    
    const nextFn = (req: HttpRequest<any>) => {
      expect(req.headers.has('Authorization')).toBe(true);
      expect(req.headers.get('Authorization')).toBe('Bearer fake-jwt-token');
      done();
      return {} as any;
    };

    TestBed.runInInjectionContext(() => {
      customJwtInterceptorFn(request, nextFn);
    });
  });
});