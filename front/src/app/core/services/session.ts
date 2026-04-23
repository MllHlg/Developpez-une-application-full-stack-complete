import { Injectable } from '@angular/core';
import { SessionInformations } from '../models/sessionInformations';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public sessionInformations: SessionInformations | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  
  constructor() {
    this.restoreSession();
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformations): void {
    this.sessionInformations = user;
    this.isLogged = true;
    localStorage.setItem('session', JSON.stringify(user));
    this.next();
  }

  public logOut(): void {
    this.sessionInformations = undefined;
    this.isLogged = false;
    localStorage.removeItem('session');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  private restoreSession(): void {
    const savedSession = localStorage.getItem('session');
    if (savedSession) {
      this.sessionInformations = JSON.parse(savedSession);
      this.isLogged = true;
      this.next;
    }
  }
}