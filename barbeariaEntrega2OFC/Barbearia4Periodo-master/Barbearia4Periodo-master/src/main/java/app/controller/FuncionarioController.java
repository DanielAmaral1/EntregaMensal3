package app.controller;

import app.entity.Funcionario;
import app.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
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
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Funcionario> adicionar(@RequestBody Funcionario funcionario) {
        Funcionario salvo = funcionarioService.save(funcionario);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(
            @PathVariable Long id,
            @RequestBody Funcionario novaFuncionario) {
        return ResponseEntity.ok(funcionarioService.update(id, novaFuncionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-nome")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(funcionarioService.buscarPorNome(nome));
    }


    @GetMapping("/by-telefone")
    public ResponseEntity<List<Funcionario>> buscarPorTelefone(@RequestParam String telefone) {
        return ResponseEntity.ok(funcionarioService.buscarPorTelefone(telefone));
    }

}
