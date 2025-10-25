package app.service;

import java.time.LocalDateTime;
import app.entity.Cliente;
import app.entity.Agendamento;
import app.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
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
    public void deleteById(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento not found with id: " + id);
        }
        agendamentoRepository.deleteById(id);
    }
    public List<Agendamento> buscarPorCliente(Cliente cliente) {
        return agendamentoRepository.findByCliente(cliente);
    }
    public List<Agendamento> buscarAgendamentosFuturos(LocalDateTime dataHoraAtual) {
        return agendamentoRepository.findByDataHoraAfter(dataHoraAtual);
    }
}
