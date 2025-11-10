package app.service;

import app.entity.Servico;
import app.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    @Autowired
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    // Create
    public Servico save(Servico servico) {
        try {
            return servicoRepository.save(servico);
        } catch (Exception e) {
            e.printStackTrace();  // Log do erro completo
            throw new RuntimeException("Error saving Servico", e);  // Lançar uma exceção com mensagem
        }
    }

    // Read
    public List<Servico> findAll() {
        try {
            return servicoRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar serviços: " + e.getMessage());
        }
    }

    public Servico findById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servico not found with id: " + id));
    }

    // Update
    public Servico update(Long id, Servico servicoDetails) {
        Servico servico = findById(id);
        servico.setNome(servicoDetails.getNome());
        servico.setDescricao(servicoDetails.getDescricao());
        servico.setPreco(servicoDetails.getPreco());
        servico.setDuracaoMinutos(servicoDetails.getDuracaoMinutos());
        return servicoRepository.save(servico);
    }
   
    public List<Servico> buscarPorNome(String nome) {
        return servicoRepository.findByNomeIgnoreCaseContaining(nome);
    }

    public List<Servico> buscarPorPrecoAte(java.math.BigDecimal preco) {
        return servicoRepository.findByPrecoLessThanEqual(preco);
    }
    
    // Delete
    public void deleteById(Long id) {
        if (!servicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Servico not found with id: " + id);
        }
        servicoRepository.deleteById(id);
    }
    public Long buscarQtdServico(){
        return servicoRepository.buscarQtdServico();
    }


}
