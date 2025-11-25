import { Injectable, Signal, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, map, tap } from 'rxjs';

interface LoginResponse {
  username: string;
  roles: string[];
  token: string;
  expiresIn: number;
  expiresAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  private readonly apiUrl = 'http://localhost:8086/api/auth';
  private readonly tokenKey = 'barbearia.token';
  private readonly expiresKey = 'barbearia.token.exp';
  private readonly rolesKey = 'barbearia.roles';
  private readonly usernameKey = 'barbearia.username';

  private readonly authenticated = signal(this.hasValidToken());
  readonly authenticatedState: Signal<boolean> = computed(() => this.authenticated());

  login(username: string, password: string): Observable<void> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password }).pipe(
      tap(response => this.persistSession(response)),
      tap(() => this.authenticated.set(true)),
      map(() => void 0)
    );
  }

  logout(redirect = true): void {
    this.clearSession();
    this.authenticated.set(false);
    if (redirect) {
      this.router.navigate(['/login']);
    }
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getRoles(): string[] {
    const roles = localStorage.getItem(this.rolesKey);
    return roles ? JSON.parse(roles) : [];
  }

  getUsername(): string | null {
    return localStorage.getItem(this.usernameKey);
  }

  isSessionValid(): boolean {
    const valid = this.hasValidToken();

    if (!valid) {
      if (this.authenticated()) {
        this.clearSession();
        this.authenticated.set(false);
      }
      return false;
    }

    if (!this.authenticated()) {
      this.authenticated.set(true);
    }

    return true;
  }

  isAuthenticated(): boolean {
    return this.isSessionValid();
  }

  private persistSession(response: LoginResponse): void {
    localStorage.setItem(this.tokenKey, response.token);
    localStorage.setItem(this.rolesKey, JSON.stringify(response.roles ?? []));
    localStorage.setItem(this.usernameKey, response.username ?? '');

    const expiresAtFromToken = this.getTokenExpiration(response.token);
    const expiresAt = expiresAtFromToken ?? this.parseIsoDate(response.expiresAt);
    if (expiresAt) {
      localStorage.setItem(this.expiresKey, expiresAt.toString());
    }
  }

  private hasValidToken(): boolean {
    const token = localStorage.getItem(this.tokenKey);
    if (!token) {
      return false;
    }
    const expiresAt = this.getStoredExpiration() ?? this.getTokenExpiration(token);
    if (!expiresAt) {
      return false;
    }
    return expiresAt > Date.now();
  }

  private getStoredExpiration(): number | null {
    const value = localStorage.getItem(this.expiresKey);
    return value ? Number(value) : null;
  }

  private getTokenExpiration(token: string): number | null {
    try {
      const payload = token.split('.')[1];
      if (!payload) {
        return null;
      }
      const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
      const decodedPayload = atob(base64);
      const claims = JSON.parse(decodedPayload);
      if (claims?.exp) {
        return claims.exp * 1000;
      }
      return null;
    } catch {
      return null;
    }
  }

  private parseIsoDate(iso?: string): number | null {
    if (!iso) {
      return null;
    }
    const date = Date.parse(iso);
    return Number.isNaN(date) ? null : date;
  }

  private clearSession(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.expiresKey);
    localStorage.removeItem(this.rolesKey);
    localStorage.removeItem(this.usernameKey);
  }
}

