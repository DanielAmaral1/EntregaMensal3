import { Component, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  nome = '';
  celular = '';
  email = '';
  password = '';
  confirmPassword = '';
  showPassword = false;
  errorMessage: string | null = null;
  successMessage: string | null = null;
  isSubmitting = false;

  onSubmit(form: NgForm): void {
    if (this.isSubmitting || form.invalid) {
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'As senhas não coincidem.';
      return;
    }

    this.errorMessage = null;
    this.successMessage = null;
    this.isSubmitting = true;

    const registerData = {
      nome: this.nome,
      celular: this.celular,
      email: this.email,
      password: this.password
    };

    this.http.post('http://localhost:8086/api/auth/register', registerData).subscribe({
      next: () => {
        this.successMessage = 'Conta criada com sucesso! Redirecionando para login...';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (error) => {
        const message = error?.error?.message || 'Erro ao criar conta.';
        this.errorMessage = typeof message === 'string' ? message : 'Erro ao criar conta.';
        this.isSubmitting = false;
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
}