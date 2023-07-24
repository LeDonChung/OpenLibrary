import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import jwtDecode from 'jwt-decode';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(public authService: AuthService,
    public router: Router,
  ) { }
 
  canActivate(route: ActivatedRouteSnapshot): boolean {
    let expectedRoleArray = route.data;
    
    expectedRoleArray = expectedRoleArray['expectedRole'];

    const token: any = this.authService.getToken();

    var tokenPayLoad: any;

    try {
      tokenPayLoad = jwtDecode(token);
    } catch (err) {
      this.authService.logout();
      this.router.navigate(['/user']);
    }

    let expectedRole = '';

    for (let i = 0; i < expectedRoleArray['length']; i++) {
      if (expectedRoleArray[i] == tokenPayLoad.roles) {
        expectedRole = tokenPayLoad.roles;
      }
    }
 
    if (tokenPayLoad.role = 'USER' || tokenPayLoad.roles == 'ADMIN') {
      if (this.authService.isAuthenticated() && tokenPayLoad.roles == expectedRole) {
        return true;
      }
      this.router.navigate(['/user']);
      return false; 
    } else { 
      this.router.navigate(['/user']);
      localStorage.clear();
      return false;
    }
  }
}
