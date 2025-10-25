import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servico } from '../../model/servicos/servico.model';

@Injectable({
  providedIn: 'root'
})
export class ServicoService {

  private apiUrl = 'http://localhost:8080/api/servicos';

  constructor(private http: HttpClient) { }

  save(servico: Servico): Observable<Servico> {
    return this.http.post<Servico>(this.apiUrl, servico);
  }

  findAll(): Observable<Servico[]> {
    return this.http.get<Servico[]>(this.apiUrl);
  }

  findById(id: number): Observable<Servico> {
    return this.http.get<Servico>(`${this.apiUrl}/${id}`);
  }

  update(id: number, servico: Servico): Observable<Servico> {
    return this.http.put<Servico>(`${this.apiUrl}/${id}`, servico);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<Servico[]> {
    return this.http.get<Servico[]>(`${this.apiUrl}/buscar?nome=${nome}`);
  }

  buscarPorPrecoAte(preco: number): Observable<Servico[]> {
    return this.http.get<Servico[]>(`${this.apiUrl}/preco-ate?preco=${preco}`);
  }

  buscarQtdServico(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/qtd`);
  }
}
