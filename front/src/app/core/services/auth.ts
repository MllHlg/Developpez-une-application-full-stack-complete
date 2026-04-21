import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/loginRequest';
import { Observable } from 'rxjs';
import { AuthSuccess } from '../models/authSuccess';
import { RegisterRequest } from '../models/registerRequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'auth';

  constructor(private httpClient: HttpClient) { }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }
}
