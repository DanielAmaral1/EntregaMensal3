import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FuncionarioTestService } from './services/funcionario/funcionario-test.service';
import { Funcionario } from './model/funcionarios/funcionario.model';

@Component({
  selector: 'app-test-funcionario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div style="padding: 20px;">
      <h2>Teste de Funcionários</h2>
      
      <div style="margin-bottom: 20px;">
        <button (click)="testarConexao()">Testar Conexão</button>
        <button (click)="listarFuncionarios()">Listar Funcionários</button>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h3>Cadastrar Funcionário</h3>
        <input [(ngModel)]="novoFuncionario.nome" placeholder="Nome" style="margin: 5px;">
        <input [(ngModel)]="novoFuncionario.telefone" placeholder="Telefone" style="margin: 5px;">
        <input [(ngModel)]="novoFuncionario.endereco" placeholder="Endereço" style="margin: 5px;">
        <button (click)="cadastrarFuncionario()">Cadastrar</button>
      </div>
      
      <div>
        <h3>Resultado:</h3>
        <pre>{{ resultado | json }}</pre>
      </div>
      
      <div>
        <h3>Funcionários ({{ funcionarios.length }}):</h3>
        <div *ngFor="let f of funcionarios" style="border: 1px solid #ccc; margin: 5px; padding: 10px;">
          <p><strong>ID:</strong> {{ f.id_funcionario }}</p>
          <p><strong>Nome:</strong> {{ f.nome }}</p>
          <p><strong>Telefone:</strong> {{ f.telefone }}</p>
          <p><strong>Endereço:</strong> {{ f.endereco }}</p>
        </div>
      </div>
    </div>
  `
})
export class TestFuncionarioComponent implements OnInit {
  funcionarios: Funcionario[] = [];
  resultado: any = {};
  
  novoFuncionario: Funcionario = {
    nome: '',
    telefone: '',
    endereco: ''
  };

  constructor(private funcionarioService: FuncionarioTestService) {}

  ngOnInit() {
    this.listarFuncionarios();
  }

  testarConexao() {
    console.log('Testando conexão...');
    this.resultado = { status: 'Testando conexão...' };
    
    this.funcionarioService.findAll().subscribe({
      next: (funcionarios) => {
        this.resultado = { 
          status: 'Sucesso!', 
          total: funcionarios.length,
          funcionarios: funcionarios 
        };
        console.log('Conexão OK:', funcionarios);
      },
      error: (error) => {
        this.resultado = { 
          status: 'Erro!', 
          error: error.message,
          details: error 
        };
        console.error('Erro na conexão:', error);
      }
    });
  }

  listarFuncionarios() {
    this.funcionarioService.findAll().subscribe({
      next: (funcionarios) => {
        this.funcionarios = funcionarios;
        console.log('Funcionários carregados:', funcionarios);
      },
      error: (error) => {
        console.error('Erro ao listar funcionários:', error);
        this.funcionarios = [];
      }
    });
  }

  cadastrarFuncionario() {
    if (this.novoFuncionario.nome && this.novoFuncionario.telefone && this.novoFuncionario.endereco) {
      this.funcionarioService.save(this.novoFuncionario).subscribe({
        next: (funcionario) => {
          console.log('Funcionário cadastrado:', funcionario);
          this.resultado = { status: 'Funcionário cadastrado!', funcionario };
          this.novoFuncionario = { nome: '', telefone: '', endereco: '' };
          this.listarFuncionarios(); // Recarrega a lista
        },
        error: (error) => {
          console.error('Erro ao cadastrar:', error);
          this.resultado = { status: 'Erro ao cadastrar!', error: error.message };
        }
      });
    } else {
      alert('Preencha todos os campos!');
    }
  }
}