package app.controller;

import app.entity.Agendamento;
import app.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@CrossOrigin(origins = "http://localhost:4200")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> create(@RequestBody Agendamento agendamento) {
        Agendamento savedAgendamento = agendamentoService.save(agendamento);
        return new ResponseEntity<>(savedAgendamento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> getAll() {
        return ResponseEntity.ok(agendamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> update(@PathVariable Long id, @RequestBody Agendamento agendamentoDetails) {
        return ResponseEntity.ok(agendamentoService.update(id, agendamentoDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agendamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
