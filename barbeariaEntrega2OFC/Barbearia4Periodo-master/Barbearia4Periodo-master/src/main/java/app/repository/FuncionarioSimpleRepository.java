package app.repository;

import app.entity.FuncionarioSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioSimpleRepository extends JpaRepository<FuncionarioSimple, Long> {
}