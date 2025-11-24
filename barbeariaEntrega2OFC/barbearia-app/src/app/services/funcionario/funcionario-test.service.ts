import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../../model/funcionarios/funcionario.model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioTestService {

  private apiUrl = 'http://localhost:8086/api/funcionarios';

  constructor(private http: HttpClient) { }

  // Headers sem autenticação para teste
  private getHeaders() {
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      })
    };
  }

  save(funcionario: Funcionario): Observable<Funcionario> {
    console.log('Enviando POST para:', this.apiUrl);
    console.log('Dados:', funcionario);
    return this.http.post<Funcionario>(this.apiUrl, funcionario, this.getHeaders());
  }

  findAll(): Observable<Funcionario[]> {
    console.log('Enviando GET para:', this.apiUrl);
    return this.http.get<Funcionario[]>(this.apiUrl, this.getHeaders());
  }

  findById(id: number): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.apiUrl}/${id}`, this.getHeaders());
  }

  update(id: number, funcionario: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/${id}`, funcionario, this.getHeaders());
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getHeaders());
  }
}