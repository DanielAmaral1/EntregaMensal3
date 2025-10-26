package app.repository;

import app.entity.ServicoSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoSimpleRepository extends JpaRepository<ServicoSimple, Long> {
}