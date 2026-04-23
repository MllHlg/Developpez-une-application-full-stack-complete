import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from '../models/article';
import { Observable } from 'rxjs';
import { ArticleDetail } from '../models/articleDetail';
import { commentaireContent } from '../models/commentaireContent';
import { articleCreate } from '../models/articleCreate';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private pathService = 'articles';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.pathService);
  }

  public detail(id: string): Observable<ArticleDetail> {
    return this.httpClient.get<ArticleDetail>(`${this.pathService}/${id}`);
  }

  public comment(id: string, comment: commentaireContent): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/${id}/message`, comment);
  }

  public create(article: articleCreate): Observable<void> {
    return this.httpClient.post<void>(this.pathService, article);
  }
}
