import { Component, OnInit } from '@angular/core';
import { AgendamentoService } from '../../services/agendamento/agendamento.service';
import { ClienteService } from '../../services/cliente/cliente.service';
import { FuncionarioService } from '../../services/funcionario/funcionario.service';
import { ServicoService } from '../../services/servico/servico.service';
import { AuthService } from '../../core/auth/auth.service';
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
    private servicoService: ServicoService,
    private authService: AuthService
  ) {}
  
  ngOnInit() {
    this.carregarAgendamentos();
    this.carregarClientes();
    this.carregarFuncionarios();
    this.carregarServicos();
    this.configurarClienteLogado();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  isCliente(): boolean {
    return this.authService.isCliente();
  }

  configurarClienteLogado() {
    if (this.isCliente()) {
      const clienteId = this.authService.getClienteId();
      const nomeCliente = this.authService.getUsername();
      if (clienteId && nomeCliente) {
        this.agendamento.cliente = { id_cliente: clienteId, nome: nomeCliente };
      }
    }
  }

  carregarAgendamentos() {
    console.log('Carregando agendamentos...');
    this.agendamentoService.getAll().subscribe({
      next: (agendamentos) => {
        console.log('Agendamentos carregados:', agendamentos);
        if (this.isCliente()) {
          const clienteId = this.authService.getClienteId();
          // Cliente vê apenas seus próprios agendamentos
          this.agendamentos = agendamentos.filter(a => a.cliente?.id_cliente === clienteId);
        } else {
          // Admin vê todos os agendamentos
          this.agendamentos = agendamentos;
        }
      },
      error: (error) => {
        console.error('Erro ao carregar agendamentos:', error);
        alert('Erro ao carregar agendamentos. Verifique se o backend está rodando.');
      }
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
        cliente: { id_cliente: this.agendamento.cliente.id_cliente },
        funcionario: { id_funcionario: this.agendamento.funcionario.id_funcionario },
        servico: { id_servico: this.agendamento.servico.id_servico }
      };
      
      console.log('=== DEBUG AGENDAMENTO ===');
      console.log('Cliente selecionado:', this.agendamento.cliente);
      console.log('Funcionario selecionado:', this.agendamento.funcionario);
      console.log('Servico selecionado:', this.agendamento.servico);
      console.log('Data/Hora:', this.agendamento.dataHora);
      console.log('Objeto final enviado:', JSON.stringify(agendamentoData, null, 2));
      console.log('========================');

      
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
          next: (agendamentoCriado) => {
            console.log('Agendamento criado:', agendamentoCriado);
            alert('Agendamento cadastrado com sucesso!');
            this.resetForm();
            setTimeout(() => this.carregarAgendamentos(), 500);
          },
          error: (error) => {
            console.error('=== ERRO DETALHADO ===');
            console.error('Status:', error.status);
            console.error('Mensagem:', error.message);
            console.error('Erro completo:', error);
            console.error('=====================');
            alert('Erro ao cadastrar agendamento: ' + (error.error?.message || error.message || 'Erro desconhecido'));
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
      console.log('Tentando deletar agendamento:', agendamento.id_agendamento);
      this.agendamentoService.delete(agendamento.id_agendamento).subscribe({
        next: () => {
          alert('Agendamento deletado com sucesso!');
          this.carregarAgendamentos();
        },
        error: (error) => {
          console.error('Erro ao deletar agendamento:', error);
          const mensagem = error.error?.message || error.message || 'Erro desconhecido ao deletar agendamento';
          alert(mensagem);
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
    this.configurarClienteLogado();
    this.mostrarFormulario = false;
  }
}
