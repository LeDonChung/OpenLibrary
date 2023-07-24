import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(
    private router: Router, 
    private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.authService.getToken();
     
    // Nếu token không null
    // Ta sẽ set request chứa token đó để kiểm tra
    if(token) {
      request = request.clone({
        setHeaders: {Authorization: `Bearer ${token}`}
      })
    }

    return next.handle(request).pipe(
      catchError((err)=>{
        if(err instanceof HttpErrorResponse) {
          console.log(err.url);
          if(err.status === 401 || err.status === 403) {
            if(this.router.url === '/user') {}
            else {
              this.authService.logout();
              this.router.navigate(['/user']);
            }
          }
        }
        return throwError(err);
      })
    )
  }
}
