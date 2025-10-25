import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../../model/clientes/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) { }

  save(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.apiUrl, cliente);
  }

  findAll(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrl);
  }

  findById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

  update(id: number, cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/${id}`, cliente);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/by-nome?nome=${nome}`);
  }

  buscarPorIdade(idade: number): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/by-idade?idade=${idade}`);
  }

  pesquisarGlobal(termo: string): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/pesquisar?termo=${termo}`);
  }
}