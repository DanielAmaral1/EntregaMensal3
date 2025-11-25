import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private readonly authService: AuthService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const isAuthRequest = req.url.includes('/auth/login');
    const token = this.authService.getToken();

    const authReq = token && !isAuthRequest
      ? req.clone({
          setHeaders: {
            Authorization: `Bearer ${token}`
          }
        })
      : req;

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        console.log('HTTP Error:', error.status, error.message);
        console.log('Request URL:', req.url);
        console.log('Token present:', !!token);
        if (error.status === 401 && !isAuthRequest) {
          console.log('401 error - logging out');
          this.authService.logout();
        }
        return throwError(() => error);
      })
    );
  }
}

