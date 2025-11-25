package app.repository;

import app.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository provides all basic CRUD operations:
    // - findAll() - List all Clientes
    // - findById() - Find a Cliente by ID
    // - save() - Save a new Cliente or update an existing one
    // - deleteById() - Delete a Cliente by ID
    // - delete() - Delete a Cliente entity
    
    // Métodos automáticos (mínimo 2 por repository)
    List<Cliente> findByNomeContaining(String nome);



    // Pesquisa por email
    List<Cliente> findByEmailContainingIgnoreCase(String email);
    
    // Pesquisa por celular
    List<Cliente> findByCelularContaining(String celular);
    
    // Buscar cliente por email para login
    Optional<Cliente> findByEmail(String email);
    
    // Verificar se email já existe
    boolean existsByEmail(String email);


}