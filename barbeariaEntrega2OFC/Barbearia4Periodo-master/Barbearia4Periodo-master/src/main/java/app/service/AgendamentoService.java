package app.service;

import java.time.LocalDateTime;
import app.entity.Cliente;
import app.entity.Agendamento;
import app.repository.AgendamentoRepository;
import app.repository.AvaliacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, AvaliacaoRepository avaliacaoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // Create
    public Agendamento save(Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    // Read
    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }
 // Read by id
    public Agendamento findById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento not found with id: " + id));
    }


    // Update
    public Agendamento update(Long id, Agendamento agendamentoDetails) {
        Agendamento agendamento = findById(id);
        agendamento.setDataHora(agendamentoDetails.getDataHora());
        agendamento.setObservacoes(agendamentoDetails.getObservacoes());
        agendamento.setCliente(agendamentoDetails.getCliente());
        agendamento.setFuncionario(agendamentoDetails.getFuncionario());
        agendamento.setServico(agendamentoDetails.getServico());
        agendamento.setProdutos(agendamentoDetails.getProdutos());
        return agendamentoRepository.save(agendamento);
    }


    // Delete
    @Transactional
    public void deleteById(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento not found with id: " + id);
        }
        
        // Deletar avaliações vinculadas primeiro
        avaliacaoRepository.deleteByAgendamentoId(id);
        
        // Depois deletar o agendamento
        agendamentoRepository.deleteById(id);
    }
    public List<Agendamento> buscarPorCliente(Cliente cliente) {
        return agendamentoRepository.findByCliente(cliente);
    }
    public List<Agendamento> buscarAgendamentosFuturos(LocalDateTime dataHoraAtual) {
        return agendamentoRepository.findByDataHoraAfter(dataHoraAtual);
    }
}
