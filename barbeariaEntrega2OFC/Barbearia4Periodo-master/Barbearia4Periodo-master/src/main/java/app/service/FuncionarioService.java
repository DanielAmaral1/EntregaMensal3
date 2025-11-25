package app.service;

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
import app.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, AgendamentoRepository agendamentoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionario not found with id: " + id));
    }

    public Funcionario update(Long id, Funcionario novaFuncionario) {
        Funcionario funcionario = findById(id);
        funcionario.setNome(novaFuncionario.getNome());
        funcionario.setEndereco(novaFuncionario.getEndereco());
        funcionario.setTelefone(novaFuncionario.getTelefone());
        return funcionarioRepository.save(funcionario);
    }

    public void delete(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Funcionario not found with id: " + id);
        }
        
        // Verificar se há agendamentos vinculados
        long agendamentosCount = agendamentoRepository.countByFuncionarioId(id);
        if (agendamentosCount > 0) {
            throw new DataIntegrityViolationException("Não é possível excluir o funcionário pois existem " + agendamentosCount + " agendamento(s) vinculado(s) a ele.");
        }
        
        funcionarioRepository.deleteById(id);
    }

    public List<Funcionario> buscarPorNome(String nome) {
        return  funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Funcionario> buscarPorTelefone(String telefone){
        return funcionarioRepository.findByTelefoneContaining(telefone);
}
}
