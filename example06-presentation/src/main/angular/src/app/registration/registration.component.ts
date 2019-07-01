import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { SessionAuthService } from '../auth/session-auth.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AngularComponent } from '../angular/angular.component';
import { AuthNewsService } from '../auth/auth-news.service';
import { environment as env } from '../../environments/environment';
import { map, tap, catchError } from 'rxjs/operators';
import { JwtAuthService } from '../auth/jwt-auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.sass']
})
export class RegistrationComponent{

  constructor(
	  private http: HttpClient, 
	  private router: Router
	) { }

  @Output()
  public username:string = "";
  public password:string = "";
  public firstname:string= "";
  public lastname:string="";
  public email:string="";
  public errors: string ="";

  register = async (e: Event) => {
	this.errors = "";  
	const emailOk = this.checkEmail();
	const usernameOk = this.checkUsername();
	const passwordOk  = this.checkPassword();
	if(emailOk && usernameOk && passwordOk) {
		e.preventDefault();
		const body = new HttpParams()
		.set('username', this.username)
		.set('password', this.password)
		.set('firstname', this.firstname)
		.set('lastname', this.lastname)
		.set('email', this.email);

		const headers = new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'});
		await this.http.post(`example06/rest/auth/user/register`, body.toString(), {headers, responseType: 'text'})
		.subscribe(
			data => {
				alert('Registered successfully');
				this.router.navigate(['login']);
			},
			error => {alert('Fehler'); console.log(error); }
		);
	}
  }

  checkEmail() {
	if (this.email == "test@test.com") {
		return true;
	} else {
		this.errors += "- Email is not valid.\n";
		return false;
	}
}

  checkUsername() {
	if (this.username != "john.doe") {
		return true;
	} else {
		this.errors += "- Username is already taken.\n";
		return false;
	}
  }

  checkPassword() {
	if (this.password.length < 8) {
		return true;
	} else {
		this.errors += "- password is too weak.\n";
		return false;
	}
  }

  getBaseUrl(): string {
    return `${env.apiUrl}/auth/jwt`
  }
}
