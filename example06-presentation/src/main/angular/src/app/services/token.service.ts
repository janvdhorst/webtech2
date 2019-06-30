import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

handle(token) {
	this.set(token);
}

set(token) {
	sessionStorage.setItem('token', token);
}

get() {
	return sessionStorage.getItem('token');
}

remove() {
	sessionStorage.removeItem('token');
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
