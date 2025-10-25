import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicos',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './servicos.html',
  styleUrls: ['./servicos.css']
})
export class ServicosComponent {
  mostrarFormulario = false;
  
  servico = {
    nome: '',
    descricao: '',
    preco: null,
    duracaoMinutos: null
  };
  
  servicos: any[] = [];
  
  cadastrarServico() {
    if (this.servico.nome && this.servico.descricao && this.servico.preco) {
      this.servicos.push({ ...this.servico });
      this.servico = { nome: '', descricao: '', preco: null, duracaoMinutos: null };
      this.mostrarFormulario = false;
      alert('Serviço cadastrado com sucesso!');
    }
  }
}
