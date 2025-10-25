import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Agendamento } from '../../model/agendamentos/agendamento.model';

@Injectable({
  providedIn: 'root'
})
export class AgendamentoService {

  private apiUrl = 'http://localhost:8080/api/agendamento';

  constructor(private http: HttpClient) { }

  create(agendamento: Agendamento): Observable<Agendamento> {
    return this.http.post<Agendamento>(this.apiUrl, agendamento);
  }

  getAll(): Observable<Agendamento[]> {
    return this.http.get<Agendamento[]>(this.apiUrl);
  }

  getById(id: number): Observable<Agendamento> {
    return this.http.get<Agendamento>(`${this.apiUrl}/${id}`);
  }

  update(id: number, agendamento: Agendamento): Observable<Agendamento> {
    return this.http.put<Agendamento>(`${this.apiUrl}/${id}`, agendamento);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getByCliente(cliente: string): Observable<Agendamento[]> {
    return this.http.get<Agendamento[]>(`${this.apiUrl}/cliente/${cliente}`);
  }

  getByFuncionario(funcionario: string): Observable<Agendamento[]> {
    return this.http.get<Agendamento[]>(`${this.apiUrl}/funcionario/${funcionario}`);
  }

  getByStatus(status: string): Observable<Agendamento[]> {
    return this.http.get<Agendamento[]>(`${this.apiUrl}/status/${status}`);
  }
}
