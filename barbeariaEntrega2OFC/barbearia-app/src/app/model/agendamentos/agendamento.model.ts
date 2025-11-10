export interface Agendamento {
  id_agendamento?: number;
  dataHora: string;
  observacoes?: string;
  cliente: {
    id_cliente?: number;
    nome: string;
  };
  funcionario: {
    id_funcionario?: number;
    nome: string;
  };
  servico: {
    id_servico?: number;
    nome: string;
  };
}