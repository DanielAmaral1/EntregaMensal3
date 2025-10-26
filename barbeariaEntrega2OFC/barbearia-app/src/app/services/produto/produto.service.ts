import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto } from '../../model/produtos/produto.model';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  private apiUrl = 'http://localhost:8080/api/produtos';

  constructor(private http: HttpClient) { }

  save(produto: Produto): Observable<Produto> {
    return this.http.post<Produto>(this.apiUrl, produto);
  }

  findAll(): Observable<Produto[]> {
    return this.http.get<Produto[]>('http://localhost:8080/api/test-simple/produtos');
  }

  findById(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.apiUrl}/${id}`);
  }

  update(id: number, produto: Produto): Observable<Produto> {
    return this.http.put<Produto>(`${this.apiUrl}/${id}`, produto);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/buscar?nome=${nome}`);
  }

  buscarPorPrecoAte(preco: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/preco-ate?preco=${preco}`);
  }

  buscarProdutosComEstoqueBaixo(quantidade: number): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}/estoque-baixo?quantidade=${quantidade}`);
  }
}
