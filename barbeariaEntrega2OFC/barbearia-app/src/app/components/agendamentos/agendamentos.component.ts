import { Component, OnInit } from '@angular/core';
import { AgendamentoService } from '../../services/agendamento/agendamento.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { FuncionarioService } from '../../services/funcionario/funcionario.service';
import { ServicoService } from '../../services/servico/servico.service';
import { Agendamento } from '../../model/agendamentos/agendamento.model';
import { Cliente } from '../../model/clientes/cliente.model';
import { Funcionario } from '../../model/funcionarios/funcionario.model';
import { Servico } from '../../model/servicos/servico.model';

@Component({
  selector: 'app-agendamentos',
  standalone: false,
  templateUrl: './agendamentos.component.html',
  styleUrls: ['./agendamentos.component.css']
})
export class AgendamentosComponent implements OnInit {
  mostrarFormulario = false;
  editandoAgendamento = false;
  agendamentoEditandoId?: number;
  
  agendamento: Agendamento = {
    dataHora: '',
    observacoes: '',
    cliente: { nome: '' },
    funcionario: { nome: '' },
    servico: { nome: '' }
  };
  
  agendamentos: Agendamento[] = [];
  clientes: Cliente[] = [];
  funcionarios: Funcionario[] = [];
  servicos: Servico[] = [];

  constructor(
    private agendamentoService: AgendamentoService,
    private clienteService: ClienteService,
    private funcionarioService: FuncionarioService,
    private servicoService: ServicoService
  ) {}
  
  ngOnInit() {
    this.carregarAgendamentos();
    this.carregarClientes();
    this.carregarFuncionarios();
    this.carregarServicos();
  }

  carregarAgendamentos() {
    this.agendamentoService.getAll().subscribe({
      next: (agendamentos) => this.agendamentos = agendamentos,
      error: (error) => console.error('Erro ao carregar agendamentos:', error)
    });
  }

  carregarClientes() {
    this.clienteService.findAll().subscribe({
      next: (clientes) => this.clientes = clientes,
      error: (error) => console.error('Erro ao carregar clientes:', error)
    });
  }

  carregarFuncionarios() {
    this.funcionarioService.findAll().subscribe({
      next: (funcionarios) => this.funcionarios = funcionarios,
      error: (error) => console.error('Erro ao carregar funcionários:', error)
    });
  }

  carregarServicos() {
    this.servicoService.findAll().subscribe({
      next: (servicos) => this.servicos = servicos,
      error: (error) => console.error('Erro ao carregar serviços:', error)
    });
  }
  
  cadastrarAgendamento() {
    if (this.agendamento.cliente && this.agendamento.funcionario && this.agendamento.servico && this.agendamento.dataHora) {

      const agendamentoData = {
        dataHora: this.agendamento.dataHora + ':00',
        observacoes: this.agendamento.observacoes || 'Nenhuma observação',
        cliente: this.agendamento.cliente,
        funcionario: this.agendamento.funcionario,
        servico: this.agendamento.servico
      };
      
      console.log('Dados sendo enviados:');
      console.log('Cliente:', this.agendamento.cliente);
      console.log('Funcionario:', this.agendamento.funcionario);
      console.log('Servico:', this.agendamento.servico);
      console.log('Objeto completo:', agendamentoData);

      
      if (this.editandoAgendamento && this.agendamentoEditandoId) {
        this.agendamentoService.update(this.agendamentoEditandoId, agendamentoData).subscribe({
          next: () => {
            alert('Agendamento atualizado com sucesso!');
            this.resetForm();
            this.carregarAgendamentos();
          },
          error: (error) => {
            console.error('Erro ao atualizar agendamento:', error);
            alert('Erro ao atualizar agendamento!');
          }
        });
      } else {
        this.agendamentoService.create(agendamentoData).subscribe({
          next: () => {
            alert('Agendamento cadastrado com sucesso!');
            this.resetForm();
            this.carregarAgendamentos();
          },
          error: (error) => {
            console.error('Erro ao cadastrar agendamento:', error);
            alert('Erro ao cadastrar agendamento!');
          }
        });
      }
    }
  }
  
  editarAgendamento(agendamento: Agendamento) {
    this.agendamento = { ...agendamento };
    this.editandoAgendamento = true;
    this.agendamentoEditandoId = agendamento.id_agendamento;
    this.mostrarFormulario = true;
  }
  
  deletarAgendamento(agendamento: Agendamento) {
    if (confirm('Tem certeza que deseja deletar este agendamento?') && agendamento.id_agendamento) {
      this.agendamentoService.delete(agendamento.id_agendamento).subscribe({
        next: () => {
          alert('Agendamento deletado com sucesso!');
          this.carregarAgendamentos();
        },
        error: (error) => {
          console.error('Erro ao deletar agendamento:', error);
          alert('Erro ao deletar agendamento!');
        }
      });
    }
  }
  
  cancelarEdicao() {
    this.resetForm();
  }

  private resetForm() {
    this.editandoAgendamento = false;
    this.agendamentoEditandoId = undefined;
    this.agendamento = { dataHora: '', observacoes: '', cliente: null as any, funcionario: null as any, servico: null as any };
    this.mostrarFormulario = false;
  }
}
