package app.service;

import app.entity.Avaliacao;
import app.repository.AvaliacaoRepository;
import app.repository.ClienteRepository;
import app.repository.AgendamentoRepository;
import app.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClienteRepository clienteRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            ClienteRepository clienteRepository,
            AgendamentoRepository agendamentoRepository,
            FuncionarioRepository funcionarioRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.clienteRepository = clienteRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public Avaliacao save(Avaliacao avaliacao) {
        if (avaliacao.getCliente() != null && avaliacao.getCliente().getId_cliente() != null && avaliacao.getCliente().getId_cliente() > 0) {
            avaliacao.setCliente(clienteRepository.findById(avaliacao.getCliente().getId_cliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
        } else {
            throw new IllegalArgumentException("Cliente é obrigatório e deve ser válido");
        }
        
        if (avaliacao.getAgendamento() != null && avaliacao.getAgendamento().getId_agendamento() != null && avaliacao.getAgendamento().getId_agendamento() > 0) {
            avaliacao.setAgendamento(agendamentoRepository.findById(avaliacao.getAgendamento().getId_agendamento())
                    .orElse(null));
        } else {
            avaliacao.setAgendamento(null);
        }
        
        if (avaliacao.getFuncionario() != null && avaliacao.getFuncionario().getId_funcionario() != null && avaliacao.getFuncionario().getId_funcionario() > 0) {
            avaliacao.setFuncionario(funcionarioRepository.findById(avaliacao.getFuncionario().getId_funcionario())
                    .orElse(null));
        } else {
            avaliacao.setFuncionario(null);
        }
        
        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> findAll() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao update(Long id, Avaliacao avaliacaoDetails) {
        Avaliacao avaliacao = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada com id: " + id));

        avaliacao.setNota(avaliacaoDetails.getNota());
        avaliacao.setComentario(avaliacaoDetails.getComentario());

        if (avaliacaoDetails.getAgendamento() != null && avaliacaoDetails.getAgendamento().getId_agendamento() != null) {
            avaliacao.setAgendamento(agendamentoRepository.findById(avaliacaoDetails.getAgendamento().getId_agendamento())
                    .orElse(null));
        }

        if (avaliacaoDetails.getFuncionario() != null && avaliacaoDetails.getFuncionario().getId_funcionario() != null) {
            avaliacao.setFuncionario(funcionarioRepository.findById(avaliacaoDetails.getFuncionario().getId_funcionario())
                    .orElse(null));
        }

        return avaliacaoRepository.save(avaliacao);
    }

    public void deleteById(Long id) {
        avaliacaoRepository.deleteById(id);
    }

    public List<Avaliacao> findByCliente(Long idCliente) {
        return avaliacaoRepository.findByClienteId(idCliente);
    }

    public List<Avaliacao> findByAgendamento(Long idAgendamento) {
        return avaliacaoRepository.findByAgendamentoId(idAgendamento);
    }

    public List<Avaliacao> findByFuncionario(Long idFuncionario) {
        return avaliacaoRepository.findByFuncionarioId(idFuncionario);
    }

    public List<Avaliacao> findByNotaMinima(Integer nota) {
        return avaliacaoRepository.findByNotaGreaterThanEqual(nota);
    }

    public List<Avaliacao> findByNotaRange(Integer notaMin, Integer notaMax) {
        return avaliacaoRepository.findByNotaBetween(notaMin, notaMax);
    }

    public List<Avaliacao> findAllOrderByDataDesc() {
        return avaliacaoRepository.findByOrderByDataAvaliacaoDesc();
    }

    public Double calcularMediaNotas() {
        List<Avaliacao> todas = findAll();
        if (todas.isEmpty()) {
            return 0.0;
        }
        return todas.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .orElse(0.0);
    }
}

