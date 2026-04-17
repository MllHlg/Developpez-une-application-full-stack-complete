import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'user'

  constructor(private httpClient: HttpClient) { }

  public user(): Observable<User> {
    return this.httpClient.get<User>(this.pathService);
  }
}
