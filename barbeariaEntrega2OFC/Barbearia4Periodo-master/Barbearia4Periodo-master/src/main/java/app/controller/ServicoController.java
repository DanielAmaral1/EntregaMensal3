package app.controller;

import app.entity.Servico;
import app.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<Servico> createServico(@RequestBody Servico servico) {
        Servico savedServico = servicoService.save(servico);
        return new ResponseEntity<>(savedServico, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> getAllServicos() {
        try {
            List<Servico> servicos = servicoService.findAll();
            return ResponseEntity.ok(servicos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

 
    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServicoById(@PathVariable Long id) {
        return new ResponseEntity<>(servicoService.findById(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Servico> updateServico(
            @PathVariable Long id,
            @RequestBody Servico servicoDetails) {
        return new ResponseEntity<>(servicoService.update(id, servicoDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        servicoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Servico>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(servicoService.buscarPorNome(nome));
    }

    @GetMapping("/preco-ate")
    public ResponseEntity<List<Servico>> buscarPorPrecoAte(@RequestParam BigDecimal preco) {
        return ResponseEntity.ok(servicoService.buscarPorPrecoAte(preco));
    }

    @GetMapping("/qtd")
    public ResponseEntity<Long> buscarQtdServico() {
        return ResponseEntity.ok(servicoService.buscarQtdServico());
    }
}
