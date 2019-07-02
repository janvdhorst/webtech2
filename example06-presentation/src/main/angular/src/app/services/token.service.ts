import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

    constructor() { }

	handle(token, username) {
		this.set(token);
		this.setUsername(username);
  }
  
  setAdmin(pAdmin) {
    sessionStorage.setItem('adm', pAdmin);
  }
  getAdmin() {
    return sessionStorage.getItem('adm');
  }

	setUsername(username) {
		sessionStorage.setItem('username', username);
	}

	getUsername() {
		return sessionStorage.getItem('username');
	}

	set(token) {
		sessionStorage.setItem('token', token);
	}

	get() {
		return sessionStorage.getItem('token');
	}

	remove() {
		sessionStorage.removeItem('token');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('adm');
	}

	isValid() {
		const token = this.get();
		if(token) {
			return true;
		} else {
			return false;
		}
	}

	isLoggedIn() {
		return this.isValid();
	}

	payload(token) {
		const payload = token.split('.')[1];
		return this.decode(payload);	
	}

	decode(payload) {
		return JSON.parse(atob(payload));
	}

}
