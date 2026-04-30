import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user';
import { User } from '../models/user';
import { UserFormRequest } from '../models/userFormRequest';
import { SessionInformations } from '../models/sessionInformations';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController; 

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], 
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get the user details', () => {
    const mockUser: User = {
      id: 1,
      username: 'user',
      email: 'user@test.com',
      themes: []
    };

    service.user().subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpTestingController.expectOne('user');
    expect(req.request.method).toBe('GET');

    req.flush(mockUser); 
  });

  it('should update the user details', () => {
    const updateRequest: UserFormRequest = {
      username: 'user_2',
      email: 'user_2@test.com',
      password: 'TEST_password_123'
    };

    const mockSessionResponse: SessionInformations = {
      token: 'jwt-token',
      type: 'Bearer',
      id: 1,
      username: 'user_2',
      email: 'user_2@test.com'
    };

    service.update(updateRequest).subscribe(sessionInfo => {
      expect(sessionInfo).toEqual(mockSessionResponse);
    });

    const req = httpTestingController.expectOne('user');
    
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updateRequest);

    req.flush(mockSessionResponse);
  });
});