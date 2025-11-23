package app.repository;

import app.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    List<Avaliacao> findByClienteIdCliente(Long idCliente);
    
    List<Avaliacao> findByAgendamentoIdAgendamento(Long idAgendamento);
    
    List<Avaliacao> findByFuncionarioIdFuncionario(Long idFuncionario);
    
    List<Avaliacao> findByNotaGreaterThanEqual(Integer nota);
    
    List<Avaliacao> findByNotaBetween(Integer notaMin, Integer notaMax);
    
    List<Avaliacao> findByOrderByDataAvaliacaoDesc();
}

