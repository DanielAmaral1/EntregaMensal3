package app.repository;

import app.entity.ProdutoSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSimpleRepository extends JpaRepository<ProdutoSimple, Long> {
}