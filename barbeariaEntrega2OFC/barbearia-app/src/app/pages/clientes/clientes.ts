import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './clientes.html',
  styleUrls: ['./clientes.css']
})
export class ClientesComponent {
  mostrarFormulario = false;
  
  cliente = {
    nome: '',
    celular: '',
    email: '',
    idade: null
  };
  
  clientes: any[] = [];
  
  cadastrarCliente() {
    if (this.cliente.nome && this.cliente.celular && this.cliente.email && this.cliente.idade) {
      this.clientes.push({ ...this.cliente });
      this.cliente = { nome: '', celular: '', email: '', idade: null };
      this.mostrarFormulario = false;
      alert('Cliente cadastrado com sucesso!');
    }
  }
}