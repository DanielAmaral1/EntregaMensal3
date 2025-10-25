package app.repository;

import java.util.List;
import app.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    // JpaRepository provides all basic CRUD operations:
    // - findAll() - List all Funcionarios
    // - findById() - Find a Funcionario by ID
    // - save() - Save a new Funcionario or update an existing one
    // - deleteById() - Delete a Funcionario by ID
    // - delete() - Delete a Funcionario entity

    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    List<Funcionario> findByTelefoneContaining(String telefone);

}
