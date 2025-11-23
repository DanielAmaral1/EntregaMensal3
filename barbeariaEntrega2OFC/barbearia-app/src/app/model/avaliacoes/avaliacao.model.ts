export interface Avaliacao {
  id_avaliacao?: number;
  nota: number;
  comentario?: string;
  dataAvaliacao?: string;
  cliente?: {
    id_cliente: number;
    nome?: string;
  };
  agendamento?: {
    id_agendamento: number;
  };
  funcionario?: {
    id_funcionario: number;
    nome?: string;
  };
}

