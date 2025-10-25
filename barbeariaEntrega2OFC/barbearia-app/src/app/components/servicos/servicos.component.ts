import { Component, OnInit } from '@angular/core';
import { ServicoService } from '../../services/servico/servico.service';
import { Servico } from '../../model/servicos/servico.model';

@Component({
  selector: 'app-servicos',
  standalone: false,
  templateUrl: './servicos.component.html',
  styleUrls: ['./servicos.component.css']
})
export class ServicosComponent implements OnInit {
  mostrarFormulario = false;
  editandoServico = false;
  servicoEditandoId?: number;
  
  servico: Servico = {
    nome: '',
    descricao: '',
    preco: 0,
    duracaoMinutos: 0
  };
  
  servicos: Servico[] = [];
  
  erros = {
    nome: '',
    descricao: '',
    preco: '',
    duracaoMinutos: ''
  };

  constructor(private servicoService: ServicoService) {}
  
  ngOnInit() {
    this.carregarServicos();
  }

  carregarServicos() {
    this.servicoService.findAll().subscribe({
      next: (servicos) => this.servicos = servicos,
      error: (error) => console.error('Erro ao carregar serviços:', error)
    });
  }
  
  cadastrarServico() {
    if (this.validarFormulario()) {
      if (this.editandoServico && this.servicoEditandoId) {
        this.servicoService.update(this.servicoEditandoId, this.servico).subscribe({
          next: () => {
            alert('Serviço atualizado com sucesso!');
            this.resetForm();
            this.carregarServicos();
          },
          error: (error) => {
            console.error('Erro ao atualizar serviço:', error);
            alert('Erro ao atualizar serviço!');
          }
        });
      } else {
        this.servicoService.save(this.servico).subscribe({
          next: () => {
            alert('Serviço cadastrado com sucesso!');
            this.resetForm();
            this.carregarServicos();
          },
          error: (error) => {
            console.error('Erro ao cadastrar serviço:', error);
            alert('Erro ao cadastrar serviço!');
          }
        });
      }
    }
  }
  
  editarServico(servico: Servico) {
    this.servico = { ...servico };
    this.editandoServico = true;
    this.servicoEditandoId = servico.id_servico;
    this.mostrarFormulario = true;
  }
  
  deletarServico(servico: Servico) {
    if (confirm('Tem certeza que deseja deletar este serviço?') && servico.id_servico) {
      this.servicoService.deleteById(servico.id_servico).subscribe({
        next: () => {
          alert('Serviço deletado com sucesso!');
          this.carregarServicos();
        },
        error: (error) => {
          console.error('Erro ao deletar serviço:', error);
          alert('Erro ao deletar serviço!');
        }
      });
    }
  }
  
  cancelarEdicao() {
    this.resetForm();
  }

  private resetForm() {
    this.editandoServico = false;
    this.servicoEditandoId = undefined;
    this.servico = { nome: '', descricao: '', preco: 0, duracaoMinutos: 0 };
    this.limparErros();
    this.mostrarFormulario = false;
  }

  private validarFormulario(): boolean {
    this.limparErros();
    let valido = true;
    
    if (!this.validarNome()) valido = false;
    if (!this.validarDescricao()) valido = false;
    if (!this.validarPreco()) valido = false;
    if (!this.validarDuracao()) valido = false;
    
    return valido;
  }

  private limparErros(): void {
    this.erros = { nome: '', descricao: '', preco: '', duracaoMinutos: '' };
  }

  private validarNome(): boolean {
    if (!this.servico.nome || this.servico.nome.trim().length < 3) {
      this.erros.nome = 'O nome deve ter no mínimo 3 letras!';
      return false;
    }
    return true;
  }

  private validarDescricao(): boolean {
    if (!this.servico.descricao || this.servico.descricao.trim().length === 0) {
      this.erros.descricao = 'A descrição não pode ser nula!';
      return false;
    }
    return true;
  }

  private validarPreco(): boolean {
    if (!this.servico.preco || this.servico.preco <= 0) {
      this.erros.preco = 'O preço não pode ser nulo ou zero!';
      return false;
    }
    return true;
  }

  private validarDuracao(): boolean {
    if (!this.servico.duracaoMinutos || this.servico.duracaoMinutos < 20) {
      this.erros.duracaoMinutos = 'A duração deve ser no mínimo 20 minutos!';
      return false;
    }
    return true;
  }
}