import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './produtos.html',
  styleUrls: ['./produtos.css']
})
export class ProdutosComponent {
  mostrarFormulario = false;
  
  produto = {
    nome: '',
    descricao: '',
    preco: null,
    quantidadeEstoque: null
  };
  
  produtos: any[] = [];
  
  cadastrarProduto() {
    if (this.produto.nome && this.produto.descricao && this.produto.preco && this.produto.quantidadeEstoque) {
      this.produtos.push({ ...this.produto });
      this.produto = { nome: '', descricao: '', preco: null, quantidadeEstoque: null };
      this.mostrarFormulario = false;
      alert('Produto cadastrado com sucesso!');
    }
  }
}
