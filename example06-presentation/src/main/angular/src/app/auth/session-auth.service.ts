import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpHeaders, HttpParams } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class SessionAuthService extends AuthService {
  private loggedIn: boolean = false;

  login(username: string, password: string): Observable<boolean> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    return this.http.post(`${env.baseUrl}/login.jsp`, body.toString(), {headers, responseType: 'text'}).pipe(
      map(() => {
        this.loggedIn = true;
        return true;
      })
    );
  }

  logout(): Observable<boolean> {
    return this.http.get(`${env.baseUrl}/logout`).pipe(
      catchError(err => {
        return err.status == 0 ? of([]) : throwError(err);
      }),
      map(() => {
        this.loggedIn = false;
        return true;
      })
    );
  }

  getAuthHeaders(): HttpHeaders {
    return new HttpHeaders();
  }

  getBaseUrl(): string {
    return `${env.apiUrl}/auth/session`
  }

  get isLoggedIn(): boolean {
    return this.loggedIn;
  }
}
