import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from '../models/article';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private pathService = 'articles';
  
  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Article[]> {
      return this.httpClient.get<Article[]>(this.pathService);
    }

}
