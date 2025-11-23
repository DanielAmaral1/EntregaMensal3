package app.controller;

import app.entity.Avaliacao;
import app.service.AvaliacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<Avaliacao> createAvaliacao(@RequestBody Avaliacao avaliacao) {
        Avaliacao savedAvaliacao = avaliacaoService.save(avaliacao);
        return new ResponseEntity<>(savedAvaliacao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> getAllAvaliacoes() {
        return ResponseEntity.ok(avaliacaoService.findAllOrderByDataDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> findById(@PathVariable Long id) {
        return avaliacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada com id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> updateAvaliacao(
            @PathVariable Long id,
            @RequestBody Avaliacao avaliacaoDetails) {
        return ResponseEntity.ok(avaliacaoService.update(id, avaliacaoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        avaliacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Avaliacao>> findByCliente(@PathVariable Long idCliente) {
        return ResponseEntity.ok(avaliacaoService.findByCliente(idCliente));
    }

    @GetMapping("/agendamento/{idAgendamento}")
    public ResponseEntity<List<Avaliacao>> findByAgendamento(@PathVariable Long idAgendamento) {
        return ResponseEntity.ok(avaliacaoService.findByAgendamento(idAgendamento));
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Avaliacao>> findByFuncionario(@PathVariable Long idFuncionario) {
        return ResponseEntity.ok(avaliacaoService.findByFuncionario(idFuncionario));
    }

    @GetMapping("/nota-minima/{nota}")
    public ResponseEntity<List<Avaliacao>> findByNotaMinima(@PathVariable Integer nota) {
        return ResponseEntity.ok(avaliacaoService.findByNotaMinima(nota));
    }

    @GetMapping("/nota-range")
    public ResponseEntity<List<Avaliacao>> findByNotaRange(
            @RequestParam Integer notaMin,
            @RequestParam Integer notaMax) {
        return ResponseEntity.ok(avaliacaoService.findByNotaRange(notaMin, notaMax));
    }

    @GetMapping("/media")
    public ResponseEntity<Map<String, Double>> getMediaNotas() {
        Double media = avaliacaoService.calcularMediaNotas();
        return ResponseEntity.ok(Map.of("media", media));
    }
}

