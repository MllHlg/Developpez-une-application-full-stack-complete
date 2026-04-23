import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Theme } from '../models/theme';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private pathService = 'themes'

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.pathService);
  }

  public abonnement(id: number): Observable<ArrayBuffer> {
    return this.httpClient.post<ArrayBuffer>(`${this.pathService}/${id}/abonnement`, null);
  }

  public desabonnement(id: number): Observable<ArrayBuffer> {
    return this.httpClient.delete<ArrayBuffer>(`${this.pathService}/${id}/abonnement`);
  }
}
