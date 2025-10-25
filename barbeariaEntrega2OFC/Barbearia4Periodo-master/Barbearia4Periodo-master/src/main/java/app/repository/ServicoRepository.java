package app.repository;

import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import app.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    // JpaRepository provides all basic CRUD operations:
    // - findAll() - List all Servicos
    // - findById() - Find a Servico by ID
    // - save() - Save a new Servico or update an existing one
    // - deleteById() - Delete a Servico by ID
    // - delete() - Delete a Servico entity

    //metodos de filtragem sem @Query
    List<Servico> findByNomeIgnoreCaseContaining(String nome);
    List<Servico> findByPrecoLessThanEqual(BigDecimal preco);

    //query para buscar a quantidade de servico que a barbearia possui
    @Query("SELECT COUNT(s) FROM Servico s ")
    Long buscarQtdServico();
}
