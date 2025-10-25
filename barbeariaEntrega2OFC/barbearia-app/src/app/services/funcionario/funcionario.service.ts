import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../../model/funcionarios/funcionario.model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {

  private apiUrl = 'http://localhost:8080/api/funcionarios';

  constructor(private http: HttpClient) { }

  save(funcionario: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.apiUrl, funcionario);
  }

  findAll(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.apiUrl);
  }

  findById(id: number): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.apiUrl}/${id}`);
  }

  update(id: number, funcionario: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/${id}`, funcionario);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/by-nome?nome=${nome}`);
  }

  buscarPorTelefone(telefone: string): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${this.apiUrl}/by-telefone?telefone=${telefone}`);
  }
}
