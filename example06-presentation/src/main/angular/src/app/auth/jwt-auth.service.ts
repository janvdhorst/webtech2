import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class JwtAuthService extends AuthService {
  private token: string;

  login(username: string, password: string): Observable<boolean> {
    return this.http.post(`${env.apiUrl}/jwt/authenticate`, {username, password}, {responseType: 'text'})
      .pipe(map(body => {
        this.token = body;
        return true;
      }));
  }

  logout(): Observable<boolean> {
    this.token = null;
    return of(true);
  }

  getAuthHeaders(): HttpHeaders {
    return this.token == null ? new HttpHeaders() : new HttpHeaders({
      "Authorization": `Bearer ${this.token}`
    });
  }

  getBaseUrl(): string {
    return `${env.apiUrl}/auth/jwt`;
  }

  get isLoggedIn(): boolean {
    return this.token != null;
  }
}
