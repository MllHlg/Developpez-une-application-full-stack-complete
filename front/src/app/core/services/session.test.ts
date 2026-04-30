import { TestBed } from '@angular/core/testing';
import { SessionService } from './session';
import { SessionInformations } from '../models/sessionInformations';

describe('SessionService', () => {
  let service: SessionService;

  const mockUser: SessionInformations = {
    token: 'jwt-token',
    type: 'Bearer',
    id: 1,
    username: 'user',
    email: 'user@test.com'
  } as SessionInformations;

  beforeEach(() => {
    localStorage.clear();

    TestBed.configureTestingModule({
      providers: [SessionService]
    });
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log in a user and save to localStorage', () => {
    const setItemSpy = jest.spyOn(Storage.prototype, 'setItem');
    service.logIn(mockUser);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformations).toEqual(mockUser);
    expect(setItemSpy).toHaveBeenCalledWith('session', JSON.stringify(mockUser));
  });

  it('should log out a user and remove from localStorage', () => {
    service.logIn(mockUser);
    const removeItemSpy = jest.spyOn(Storage.prototype, 'removeItem');
    
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformations).toBeUndefined();
    expect(removeItemSpy).toHaveBeenCalledWith('session');
  });

  it('should restore session if it exists', () => {
    localStorage.setItem('session', JSON.stringify(mockUser));

    const newService = new SessionService();

    expect(newService.isLogged).toBe(true);
    expect(newService.sessionInformations).toEqual(mockUser);
  });
});