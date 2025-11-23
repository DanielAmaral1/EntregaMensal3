import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Avaliacao } from '../../model/avaliacoes/avaliacao.model';

@Injectable({
  providedIn: 'root'
})
export class AvaliacaoService {

  private apiUrl = 'http://localhost:9090/api/avaliacoes';

  constructor(private http: HttpClient) { }

  save(avaliacao: Avaliacao): Observable<Avaliacao> {
    return this.http.post<Avaliacao>(this.apiUrl, avaliacao);
  }

  findAll(): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(this.apiUrl);
  }

  findById(id: number): Observable<Avaliacao> {
    return this.http.get<Avaliacao>(`${this.apiUrl}/${id}`);
  }

  update(id: number, avaliacao: Avaliacao): Observable<Avaliacao> {
    return this.http.put<Avaliacao>(`${this.apiUrl}/${id}`, avaliacao);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  findByCliente(idCliente: number): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(`${this.apiUrl}/cliente/${idCliente}`);
  }

  findByAgendamento(idAgendamento: number): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(`${this.apiUrl}/agendamento/${idAgendamento}`);
  }

  findByFuncionario(idFuncionario: number): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(`${this.apiUrl}/funcionario/${idFuncionario}`);
  }

  findByNotaMinima(nota: number): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(`${this.apiUrl}/nota-minima/${nota}`);
  }

  findByNotaRange(notaMin: number, notaMax: number): Observable<Avaliacao[]> {
    return this.http.get<Avaliacao[]>(`${this.apiUrl}/nota-range?notaMin=${notaMin}&notaMax=${notaMax}`);
  }

  getMediaNotas(): Observable<{ media: number }> {
    return this.http.get<{ media: number }>(`${this.apiUrl}/media`);
  }
}

