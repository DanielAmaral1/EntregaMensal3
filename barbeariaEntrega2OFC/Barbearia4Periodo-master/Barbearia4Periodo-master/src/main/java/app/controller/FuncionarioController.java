package app.controller;

import app.entity.Funcionario;
import app.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    @PreAuthorize("hasAuthority('MASTER') or hasAuthority('CLIENTE')")
    public ResponseEntity<List<Funcionario>> listarTodos() {
        try {
            List<Funcionario> funcionarios = funcionarioService.findAll();
            return ResponseEntity.ok(funcionarios);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Funcionario> adicionar(@RequestBody Funcionario funcionario) {
        Funcionario salvo = funcionarioService.save(funcionario);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Funcionario> atualizar(
            @PathVariable Long id,
            @RequestBody Funcionario novaFuncionario) {
        return ResponseEntity.ok(funcionarioService.update(id, novaFuncionario));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-nome")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(funcionarioService.buscarPorNome(nome));
    }


    @GetMapping("/by-telefone")
    @PreAuthorize("hasAuthority('MASTER')")
    public ResponseEntity<List<Funcionario>> buscarPorTelefone(@RequestParam String telefone) {
        return ResponseEntity.ok(funcionarioService.buscarPorTelefone(telefone));
    }

}
