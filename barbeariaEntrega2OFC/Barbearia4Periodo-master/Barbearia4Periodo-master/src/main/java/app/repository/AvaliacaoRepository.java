package app.repository;

import app.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    @Query("SELECT a FROM Avaliacao a WHERE a.cliente.id_cliente = :idCliente")
    List<Avaliacao> findByClienteId(@Param("idCliente") Long idCliente);
    
    @Query("SELECT a FROM Avaliacao a WHERE a.agendamento.id_agendamento = :idAgendamento")
    List<Avaliacao> findByAgendamentoId(@Param("idAgendamento") Long idAgendamento);
    
    @Query("SELECT a FROM Avaliacao a WHERE a.funcionario.id_funcionario = :idFuncionario")
    List<Avaliacao> findByFuncionarioId(@Param("idFuncionario") Long idFuncionario);
    
    List<Avaliacao> findByNotaGreaterThanEqual(Integer nota);
    
    List<Avaliacao> findByNotaBetween(Integer notaMin, Integer notaMax);
    
    List<Avaliacao> findByOrderByDataAvaliacaoDesc();
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Avaliacao a WHERE a.agendamento.id_agendamento = :idAgendamento")
    void deleteByAgendamentoId(@Param("idAgendamento") Long idAgendamento);
    

}

