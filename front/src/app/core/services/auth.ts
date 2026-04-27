import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../models/loginRequest';
import { Observable } from 'rxjs';
import { UserFormRequest } from '../models/userFormRequest';
import { SessionInformations } from '../models/sessionInformations';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'auth';

  constructor(private httpClient: HttpClient) { }

  public login(loginRequest: LoginRequest): Observable<SessionInformations> {
    return this.httpClient.post<SessionInformations>(`${this.pathService}/login`, loginRequest);
  }

  public register(registerRequest: UserFormRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/register`, registerRequest);
  }
}
