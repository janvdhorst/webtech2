import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { News } from '../news';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { BaseNewsService } from '../base-news.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class NewsService extends BaseNewsService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<News[]> {
    return this.http.get<any[]>(`${env.apiUrl}/news`, {headers: this.defaultHeaders}).pipe(
      map(body => body.map(n => News.fromObject(n)))
    );
  }

  getNewest(): Observable<News> {
    return this.http.get<any>(`${env.apiUrl}/news/newest`, {headers: this.defaultHeaders}).pipe(
      map(body => News.fromObject(body))
    );
  }

  create(headline: string, content: string): Observable<News> {
    return this.http.post<any>(`${env.apiUrl}/news`, {headline, content}, {headers: this.defaultHeaders}).pipe(
      map(body => News.fromObject(body))
    );
  }

  delete(news: News): Observable<News> {
    //THIS IS NOT IN USE!! FIND THE API CALL IN THE NEWS LIST COMPONENT TS FILE
    return this.http.get<any>(`${env.apiUrl}/news/newest`, {headers: this.defaultHeaders}).pipe(
      map(body => News.fromObject(body))
    );
  }

  update(news: News, content: string): Observable<News> {
    //THIS IS NOT IN USE!! FIND THE API CALL IN THE NEWS LIST COMPONENT TS FILE
    return this.http.get<any>(`${env.apiUrl}/news/newest`, {headers: this.defaultHeaders}).pipe(
      map(body => News.fromObject(body))
    );
  }
}
