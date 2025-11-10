package app.controller;

import app.entity.*;
import app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/simple")
public class SimpleController {

    @Autowired
    private FuncionarioSimpleRepository funcionarioSimpleRepository;
    
    @Autowired
    private ProdutoSimpleRepository produtoSimpleRepository;
    
    @Autowired
    private ServicoSimpleRepository servicoSimpleRepository;

    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioSimple>> getFuncionarios() {
        try {
            return ResponseEntity.ok(funcionarioSimpleRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoSimple>> getProdutos() {
        try {
            return ResponseEntity.ok(produtoSimpleRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<ServicoSimple>> getServicos() {
        try {
            return ResponseEntity.ok(servicoSimpleRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}