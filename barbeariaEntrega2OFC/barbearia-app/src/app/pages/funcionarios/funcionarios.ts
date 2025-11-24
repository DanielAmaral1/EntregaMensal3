import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FuncionarioService } from '../../services/funcionario/funcionario.service';
import { Funcionario } from '../../model/funcionarios/funcionario.model';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './funcionarios.html',
  styleUrls: ['./funcionarios.css']
})
export class FuncionariosComponent implements OnInit {
  mostrarFormulario = false;
  
  funcionario: Funcionario = {
    nome: '',
    telefone: '',
    endereco: ''
  };
  
  funcionarios: Funcionario[] = [];
  
  constructor(private funcionarioService: FuncionarioService) {}
  
  ngOnInit() {
    this.carregarFuncionarios();
  }
  
  carregarFuncionarios() {
    this.funcionarioService.findAll().subscribe({
      next: (funcionarios) => {
        this.funcionarios = funcionarios;
      },
      error: (error) => {
        console.error('Erro ao carregar funcionários:', error);
        alert('Erro ao carregar funcionários!');
      }
    });
  }
  
  cadastrarFuncionario() {
    if (this.funcionario.nome && this.funcionario.telefone && this.funcionario.endereco) {
      this.funcionarioService.save(this.funcionario).subscribe({
        next: (funcionarioSalvo) => {
          this.funcionario = { nome: '', telefone: '', endereco: '' };
          this.mostrarFormulario = false;
          alert('Funcionário cadastrado com sucesso!');
          this.carregarFuncionarios(); // Recarrega a lista
        },
        error: (error) => {
          console.error('Erro ao cadastrar funcionário:', error);
          alert('Erro ao cadastrar funcionário!');
        }
      });
    }
  }
}
