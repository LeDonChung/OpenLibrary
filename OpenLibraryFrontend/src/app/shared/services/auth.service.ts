import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { User } from '../models/User';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router) { }
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn() {
    return this.getToken() !== null;
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigate(['login']);
  }

  getUserCurrent():string {
    let userCurrent:string = "";
    let tokenPayLoad: any;
    const token: any = this.getToken();
    try {
      tokenPayLoad = jwtDecode(token);
      userCurrent = tokenPayLoad.sub;
    } catch (err) {
      this.logout();
      this.router.navigate(['/user']);
    }
    return userCurrent;
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigate(['/']);
      return false;
    }
    return true;
  }
}
