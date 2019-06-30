import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router } from '@angular/router';
import { TokenService } from './services/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit{
  title = 'example06';

	public loggedIn: boolean;

	constructor(
		private Auth: AuthService,
		  private router: Router,
		  private Token: TokenService
	) {}

	ngOnInit() {
		this.Auth.authStatus.subscribe(value => this.loggedIn = value);
	}

	logout(e:Event) {
		e.preventDefault();
		this.Token.remove();
		this.Auth.changeAuthStatus(false);
		this.router.navigate(['login']);

	}



}
