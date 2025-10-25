import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './funcionarios.html',
  styleUrls: ['./funcionarios.css']
})
export class FuncionariosComponent {
  mostrarFormulario = false;
  
  funcionario = {
    nome: '',
    telefone: '',
    endereco: ''
  };
  
  funcionarios: any[] = [];
  
  cadastrarFuncionario() {
    if (this.funcionario.nome && this.funcionario.telefone && this.funcionario.endereco) {
      this.funcionarios.push({ ...this.funcionario });
      this.funcionario = { nome: '', telefone: '', endereco: '' };
      this.mostrarFormulario = false;
      alert('Funcionário cadastrado com sucesso!');
    }
  }
}
