


package app.service;

import app.entity.Cliente;
import app.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //CREATE
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }


    // READ
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    //UPDATE
    public Cliente update(Long id, Cliente clienteDetails) {
        Cliente cliente = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + id));

        cliente.setNome(clienteDetails.getNome());
        cliente.setCelular(clienteDetails.getCelular());
        cliente.setEmail(clienteDetails.getEmail());


        return clienteRepository.save(cliente);
    }

    //DELETE
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return  clienteRepository.findByNomeContaining(nome);
    }



    public List<Cliente> pesquisarGlobal(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return findAll();
        }
        
        List<Cliente> resultados = new java.util.ArrayList<>();
        String termoLower = termo.toLowerCase().trim();
        
        // Buscar por nome
        resultados.addAll(clienteRepository.findByNomeContaining(termo));
        
        // Buscar por email
        resultados.addAll(clienteRepository.findByEmailContainingIgnoreCase(termo));
        
        // Buscar por celular
        resultados.addAll(clienteRepository.findByCelularContaining(termo));
        

        
        // Remover duplicatas
        return resultados.stream().distinct().collect(java.util.stream.Collectors.toList());
    }

}



