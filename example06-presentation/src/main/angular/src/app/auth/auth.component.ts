import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthNewsService } from './auth-news.service';
import { AngularComponent } from '../angular/angular.component';
import { JwtAuthService } from './jwt-auth.service';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';
import { SessionAuthService } from './session-auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.sass'],
  providers: [AuthNewsService]
})
export class AuthComponent extends AngularComponent implements OnInit {

  private authService: AuthService;

  constructor(private http: HttpClient,
              private authNewsService: AuthNewsService) {
    super(authNewsService);
    this.useBasicAuth();
  }

  ngOnInit() {
  }

  logout() {
    this.authService.logout().subscribe();
    this.news = [];
    this.latest = null;
  }

  useBasicAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new BasicAuthService(this.http);
    this.authNewsService.authService = this.authService;
  }

  useJwtAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new JwtAuthService(this.http);
    this.authNewsService.authService = this.authService;
  }

  useSessionAuth(e?: Event) {
    if (e != null) e.preventDefault();
    this.authService = new SessionAuthService(this.http);
    this.authNewsService.authService = this.authService;
  }

  isBasicAuth(): boolean {
    return this.authService instanceof BasicAuthService;
  }

  isJwtAuth(): boolean {
    return this.authService instanceof JwtAuthService;
  }

  isSessionAuth(): boolean {
    return this.authService instanceof SessionAuthService;
  }

  get isLoggedIn(): boolean {
    return this.authService.isLoggedIn;
  }
}
