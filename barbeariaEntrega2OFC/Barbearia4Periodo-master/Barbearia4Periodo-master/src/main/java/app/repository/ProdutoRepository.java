package app.repository;

import app.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Métodos automáticos (mínimo 2 por repository)
    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);

    // Consulta personalizada com JPQL (pelo menos 1 na aplicação)
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < :quantidade")
    List<Produto> buscarProdutosComEstoqueBaixo(@Param("quantidade") Integer quantidade);
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE CONCAT ('%', :nome, '%')")
    List<Produto> buscarPorNome(@Param("nome")String nome);

}