package app.controller;

import app.entity.Cliente;
import app.service.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.save(cliente);
        return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllCliente() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(
            @PathVariable Long id,
            @RequestBody Cliente clienteDetails) {
        return ResponseEntity.ok(clienteService.update(id, clienteDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by-nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(clienteService.buscarPorNome(nome));
    }

    @GetMapping("/by-idade")
    public ResponseEntity<List<Cliente>> buscarPorIdade(@RequestParam Integer idade) {
        return ResponseEntity.ok(clienteService.buscarPorIdade(idade));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Cliente>> pesquisarGlobal(@RequestParam String termo) {
        return ResponseEntity.ok(clienteService.pesquisarGlobal(termo));
    }

}
