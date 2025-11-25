package app.repository;

import app.entity.Agendamento;
import app.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // Métodos automáticos (mínimo 2 por repository)
    //busca por cliente
    List<Agendamento> findByCliente(Cliente cliente);
    // busca agendamentos com data/hora maior que (depois de)
    List<Agendamento> findByDataHoraAfter(LocalDateTime dataHora);
    // conta agendamentos por funcionário
    @Query("SELECT COUNT(a) FROM Agendamento a WHERE a.funcionario.id_funcionario = :funcionarioId")
    long countByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
}
