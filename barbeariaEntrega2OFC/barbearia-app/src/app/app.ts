import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.css'
})
export class AppComponent implements OnInit {
  clientes: any[] = [];
  cliente = { nome: '', celular: '', email: '', idade: null };
  backendStatus = false;
  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.carregarClientes();
  }

  carregarClientes() {
    this.http.get<any[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.clientes = data;
        this.backendStatus = true;
      },
      error: () => {
        this.backendStatus = false;
        console.log('Backend desconectado');
      }
    });
  }

  cadastrarCliente(nome: string, celular: string, email: string, idade: string) {
    const cliente = {
      nome: nome,
      celular: celular,
      email: email,
      idade: parseInt(idade)
    };
    
    this.http.post(this.apiUrl, cliente).subscribe({
      next: () => {
        this.carregarClientes();
        alert('Cliente cadastrado!');
      },
      error: () => alert('Erro ao cadastrar')
    });
  }

  deletarCliente(cliente: any) {
    if (confirm('Deletar cliente?')) {
      this.http.delete(`${this.apiUrl}/${cliente.id_cliente}`).subscribe({
        next: () => this.carregarClientes(),
        error: () => alert('Erro ao deletar')
      });
    }
  }

  testarConexao() {
    this.carregarClientes();
  }
}
