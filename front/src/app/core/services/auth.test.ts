import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth';
import { LoginRequest } from '../models/loginRequest';
import { UserFormRequest } from '../models/userFormRequest';
import { SessionInformations } from '../models/sessionInformations';

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a login request and return session informations', () => {
    const mockLoginRequest: LoginRequest = {
      identifiant: 'user',
      password: 'TEST_password_123'
    };

    const mockSessionResponse: SessionInformations = {
      token: 'jwt-token',
      type: 'Bearer',
      id: 1,
      username: 'user',
      email: 'user@test.com'
    };

    service.login(mockLoginRequest).subscribe((sessionInfo) => {
      expect(sessionInfo).toEqual(mockSessionResponse);
    });

    const req = httpTestingController.expectOne('auth/login');
    
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockLoginRequest);

    req.flush(mockSessionResponse);
  });

  it('should send a register request', () => {
    const mockRegisterRequest: UserFormRequest = {
      username: 'user_2',
      email: 'user_2@test.com',
      password: 'TEST_password_123'
    };

    service.register(mockRegisterRequest).subscribe((response) => {
      expect(response).toBeNull(); 
    });

    const req = httpTestingController.expectOne('auth/register');
    
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockRegisterRequest);
    req.flush(null);
  });
});