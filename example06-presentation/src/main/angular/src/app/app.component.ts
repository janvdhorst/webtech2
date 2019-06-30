import { Component, OnInit } from '@angular/core';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit{
  title = 'example06';

	public loggedIn: boolean;

	constructor(
		private Auth: AuthService
	) {}

	ngOnInit() {
		this.Auth.authStatus.subscribe(value => this.loggedIn = value);
	}




}
