import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
    if (!this.authService.isSessionValid()) {
      return this.router.createUrlTree(['/login']);
    }

    const requiredRole = route.data['role'];
    
    if (requiredRole === 'MASTER' && !this.authService.isAdmin()) {
      return this.router.createUrlTree(['/']);
    }

    return true;
  }
}