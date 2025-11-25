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
    servico: { nome: '', duracaoMinutos: 60 }
  };
  
  agendamentoData: string = '';
  agendamentoHora: string = '';
  
  currentMonth: number = new Date().getMonth();
  currentYear: number = new Date().getFullYear();
  selectedDate: Date | null = null;
  
  horariosBase: string[] = [
    '08:00', '08:15', '08:30', '08:45',
    '09:00', '09:15', '09:30', '09:45',
    '10:00', '10:15', '10:30', '10:45',
    '11:00', '11:15', '11:30', '11:45',
    '12:00', '12:15', '12:30', '12:45',
    '13:00', '13:15', '13:30', '13:45',
    '14:00', '14:15', '14:30', '14:45',
    '15:00', '15:15', '15:30', '15:45',
    '16:00', '16:15', '16:30', '16:45',
    '17:00', '17:15', '17:30', '17:45',
    '18:00'
  ];
  
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
    if (this.agendamento.cliente && this.agendamento.funcionario && this.agendamento.servico && this.agendamentoData && this.agendamentoHora) {
      
      // Combinar data e hora
      const dataHoraCompleta = `${this.agendamentoData}T${this.agendamentoHora}:00`;

      const agendamentoData: any = {
        dataHora: dataHoraCompleta,
        observacoes: this.agendamento.observacoes || 'Nenhuma observação',
        cliente: { id_cliente: this.agendamento.cliente.id_cliente },
        funcionario: { id_funcionario: this.agendamento.funcionario.id_funcionario },
        servico: { id_servico: this.agendamento.servico.id_servico }
      };
      
      console.log('=== DEBUG AGENDAMENTO ===');
      console.log('Cliente selecionado:', this.agendamento.cliente);
      console.log('Funcionario selecionado:', this.agendamento.funcionario);
      console.log('Servico selecionado:', this.agendamento.servico);
      console.log('Data:', this.agendamentoData);
      console.log('Hora:', this.agendamentoHora);
      console.log('Data/Hora combinada:', dataHoraCompleta);
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
    
    // Separar data e hora para edição
    if (agendamento.dataHora) {
      const dataHora = new Date(agendamento.dataHora);
      this.agendamentoData = dataHora.toISOString().split('T')[0];
      this.agendamentoHora = dataHora.toTimeString().substring(0, 5);
    }
    
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

  getCurrentMonthYear(): string {
    const months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                   'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    return `${months[this.currentMonth]} ${this.currentYear}`;
  }

  previousMonth(): void {
    if (this.currentMonth === 0) {
      this.currentMonth = 11;
      this.currentYear--;
    } else {
      this.currentMonth--;
    }
  }

  nextMonth(): void {
    if (this.currentMonth === 11) {
      this.currentMonth = 0;
      this.currentYear++;
    } else {
      this.currentMonth++;
    }
  }

  getCalendarDays(): any[] {
    const firstDay = new Date(this.currentYear, this.currentMonth, 1);
    const startDate = new Date(firstDay);
    startDate.setDate(startDate.getDate() - firstDay.getDay());
    
    const days = [];
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Zerar horas para comparação correta
    
    for (let i = 0; i < 42; i++) {
      const date = new Date(startDate.getTime() + (i * 24 * 60 * 60 * 1000));
      const isCurrentMonth = date.getMonth() === this.currentMonth;
      const dateOnly = new Date(date);
      dateOnly.setHours(0, 0, 0, 0);
      const isPastDate = dateOnly < today;
      
      days.push({
        day: date.getDate(),
        date: date,
        currentMonth: isCurrentMonth,
        enabled: isCurrentMonth && !isPastDate
      });
    }
    
    return days;
  }

  selectDay(day: any): void {
    if (day.enabled) {
      this.selectedDate = day.date;
      this.agendamentoData = day.date.toISOString().split('T')[0];
    }
  }

  isSelectedDay(day: any): boolean {
    return this.selectedDate && 
           this.selectedDate.toDateString() === day.date.toDateString();
  }

  getHorariosDisponiveis(): any[] {
    if (!this.agendamentoData || !this.agendamento.funcionario?.id_funcionario) {
      return this.horariosBase.map(h => ({ value: h, label: h, disponivel: true }));
    }

    return this.horariosBase.map(horario => {
      const disponivel = this.isHorarioDisponivel(horario);
      return {
        value: horario,
        label: horario,
        disponivel: disponivel
      };
    });
  }

  isHorarioDisponivel(horario: string): boolean {
    if (!this.agendamentoData || !this.agendamento.funcionario?.id_funcionario) {
      return true;
    }

    const dataHorarioSelecionado = new Date(`${this.agendamentoData}T${horario}:00`);
    const funcionarioId = this.agendamento.funcionario.id_funcionario;
    const servicoDuracao = this.agendamento.servico?.duracaoMinutos || 60;

    // Verificar conflitos com agendamentos existentes
    for (const agendamento of this.agendamentos) {
      // Pular o agendamento atual se estiver editando
      if (this.editandoAgendamento && agendamento.id_agendamento === this.agendamentoEditandoId) {
        continue;
      }

      // Verificar se é o mesmo funcionário
      if (agendamento.funcionario?.id_funcionario !== funcionarioId) {
        continue;
      }

      // Verificar se é o mesmo dia
      const agendamentoDataExistente = new Date(agendamento.dataHora!);
      if (agendamentoDataExistente.toDateString() !== dataHorarioSelecionado.toDateString()) {
        continue;
      }

      // Calcular horário de início e fim do agendamento existente
      const inicioExistente = agendamentoDataExistente;
      const duracaoExistente = agendamento.servico?.duracaoMinutos || 60;
      const fimExistente = new Date(inicioExistente.getTime() + duracaoExistente * 60000);

      // Calcular horário de início e fim do novo agendamento
      const inicioNovo = dataHorarioSelecionado;
      const fimNovo = new Date(inicioNovo.getTime() + servicoDuracao * 60000);

      // Verificar sobreposição de horários
      if ((inicioNovo < fimExistente) && (fimNovo > inicioExistente)) {
        return false; // Há conflito
      }
    }

    return true; // Horário disponível
  }

  onFuncionarioChange(): void {
    // Limpar horário selecionado quando funcionário mudar
    this.agendamentoHora = '';
  }

  onServicoChange(): void {
    // Limpar horário selecionado quando serviço mudar
    this.agendamentoHora = '';
  }

  private resetForm() {
    this.editandoAgendamento = false;
    this.agendamentoEditandoId = undefined;
    this.agendamento = { dataHora: '', observacoes: '', cliente: null as any, funcionario: null as any, servico: { nome: '', duracaoMinutos: 60 } };
    this.agendamentoData = '';
    this.agendamentoHora = '';
    this.selectedDate = null;
    this.configurarClienteLogado();
    this.mostrarFormulario = false;
  }
}
