import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { SessionAuthService } from '../auth/session-auth.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AngularComponent } from '../angular/angular.component';
import { AuthNewsService } from '../auth/auth-news.service';
import { environment as env } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { JwtAuthService } from '../auth/jwt-auth.service';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
import { TokenService } from '../services/token.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class NewLoginComponent{

	  
  constructor(
	  private http: HttpClient, 
	  private router: Router,
	  private Token: TokenService,
	  private Auth: AuthService
  ) { }

  @Output()
  public username:string = "";
  public password:string = "";
  public firstname:string= "";
  public lastname:string="";
  public email:string="";

  login = async (e: Event) => {
    e.preventDefault();
    const body = new HttpParams()
    .set('username', this.username)
    .set('password', this.password);

    const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
    await this.http.post(`example06/rest/auth/user/login`, body.toString(), {headers, responseType: 'text'})
    .subscribe(
      data => this.handleResponse(data),
      error => { alert('Login failed'); console.log(error); }
    );
  }

  handleResponse(data) {
	this.Token.handle(data, this.username);
	this.Auth.changeAuthStatus(true);
	this.router.navigate(['angular']);
  }

  getBaseUrl(): string {
    return `${env.apiUrl}/auth/jwt`
  }
}
