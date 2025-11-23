import { Component, OnInit } from '@angular/core';
import { AvaliacaoService } from '../../services/avaliacao/avaliacao.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { AgendamentoService } from '../../services/agendamento/agendamento.service';
import { Avaliacao } from '../../model/avaliacoes/avaliacao.model';
import { Cliente } from '../../model/clientes/cliente.model';
import { Agendamento } from '../../model/agendamentos/agendamento.model';

@Component({
  selector: 'app-avaliacoes',
  standalone: false,
  templateUrl: './avaliacoes.component.html',
  styleUrls: ['./avaliacoes.component.css']
})
export class AvaliacoesComponent implements OnInit {
  mostrarFormulario = false;
  editandoAvaliacao = false;
  avaliacaoEditandoId?: number;
  
  avaliacao: Avaliacao = {
    nota: 5,
    comentario: '',
    cliente: { id_cliente: 0 },
    agendamento: { id_agendamento: 0 }
  };
  
  avaliacoes: Avaliacao[] = [];
  avaliacoesFiltradas: Avaliacao[] = [];
  clientes: Cliente[] = [];
  agendamentos: Agendamento[] = [];
  termoPesquisa: string = '';
  mediaNotas: number = 0;
  
  erros = {
    nota: '',
    cliente: ''
  };

  constructor(
    private avaliacaoService: AvaliacaoService,
    private clienteService: ClienteService,
    private agendamentoService: AgendamentoService
  ) {}
  
  ngOnInit() {
    this.carregarAvaliacoes();
    this.carregarClientes();
    this.carregarAgendamentos();
    this.carregarMediaNotas();
  }

  carregarAvaliacoes() {
    this.avaliacaoService.findAll().subscribe({
      next: (avaliacoes) => {
        this.avaliacoes = avaliacoes;
        this.avaliacoesFiltradas = avaliacoes;
      },
      error: (error) => console.error('Erro ao carregar avaliações:', error)
    });
  }

  carregarClientes() {
    this.clienteService.findAll().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
      },
      error: (error) => console.error('Erro ao carregar clientes:', error)
    });
  }

  carregarAgendamentos() {
    this.agendamentoService.getAll().subscribe({
      next: (agendamentos) => {
        this.agendamentos = agendamentos;
      },
      error: (error) => console.error('Erro ao carregar agendamentos:', error)
    });
  }

  carregarMediaNotas() {
    this.avaliacaoService.getMediaNotas().subscribe({
      next: (response) => {
        this.mediaNotas = response.media;
      },
      error: (error) => console.error('Erro ao carregar média:', error)
    });
  }

  pesquisar() {
    if (!this.termoPesquisa.trim()) {
      this.avaliacoesFiltradas = this.avaliacoes;
      return;
    }

    const termo = this.termoPesquisa.toLowerCase();
    this.avaliacoesFiltradas = this.avaliacoes.filter(av => 
      av.comentario?.toLowerCase().includes(termo) ||
      av.cliente?.nome?.toLowerCase().includes(termo) ||
      av.nota.toString().includes(termo)
    );
  }

  limparPesquisa() {
    this.termoPesquisa = '';
    this.avaliacoesFiltradas = this.avaliacoes;
  }

  cadastrarAvaliacao() {
    if (this.validarFormulario()) {
      if (this.editandoAvaliacao && this.avaliacaoEditandoId) {
        this.avaliacaoService.update(this.avaliacaoEditandoId, this.avaliacao).subscribe({
          next: () => {
            alert('Avaliação atualizada com sucesso!');
            this.resetForm();
            this.carregarAvaliacoes();
            this.carregarMediaNotas();
          },
          error: (error) => {
            console.error('Erro ao atualizar avaliação:', error);
            alert('Erro ao atualizar avaliação!');
          }
        });
      } else {
        this.avaliacaoService.save(this.avaliacao).subscribe({
          next: () => {
            alert('Avaliação cadastrada com sucesso!');
            this.resetForm();
            this.carregarAvaliacoes();
            this.carregarMediaNotas();
          },
          error: (error) => {
            console.error('Erro ao cadastrar avaliação:', error);
            alert('Erro ao cadastrar avaliação!');
          }
        });
      }
    }
  }
  
  editarAvaliacao(avaliacao: Avaliacao) {
    this.avaliacao = { ...avaliacao };
    this.editandoAvaliacao = true;
    this.avaliacaoEditandoId = avaliacao.id_avaliacao;
    this.mostrarFormulario = true;
  }
  
  deletarAvaliacao(avaliacao: Avaliacao) {
    if (confirm('Tem certeza que deseja deletar esta avaliação?') && avaliacao.id_avaliacao) {
      this.avaliacaoService.deleteById(avaliacao.id_avaliacao).subscribe({
        next: () => {
          alert('Avaliação deletada com sucesso!');
          this.carregarAvaliacoes();
          this.carregarMediaNotas();
        },
        error: (error) => {
          console.error('Erro ao deletar avaliação:', error);
          alert('Erro ao deletar avaliação!');
        }
      });
    }
  }
  
  cancelarEdicao() {
    this.resetForm();
  }

  private resetForm() {
    this.editandoAvaliacao = false;
    this.avaliacaoEditandoId = undefined;
    this.avaliacao = { nota: 5, comentario: '', cliente: { id_cliente: 0 }, agendamento: { id_agendamento: 0 } };
    this.limparErros();
    this.mostrarFormulario = false;
  }

  private validarFormulario(): boolean {
    this.limparErros();
    let valido = true;
    
    if (!this.validarNota()) valido = false;
    if (!this.validarCliente()) valido = false;
    
    return valido;
  }

  private limparErros(): void {
    this.erros = { nota: '', cliente: '' };
  }

  private validarNota(): boolean {
    if (!this.avaliacao.nota || this.avaliacao.nota < 1 || this.avaliacao.nota > 5) {
      this.erros.nota = 'A nota deve ser entre 1 e 5!';
      return false;
    }
    return true;
  }

  private validarCliente(): boolean {
    if (!this.avaliacao.cliente || !this.avaliacao.cliente.id_cliente) {
      this.erros.cliente = 'Selecione um cliente!';
      return false;
    }
    return true;
  }

  getEstrelas(nota: number): string {
    return '⭐'.repeat(nota) + '☆'.repeat(5 - nota);
  }
}

