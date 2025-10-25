package app.controller;

import app.entity.Produto;
import app.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> save(@Valid @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.save(produto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        try {
            List<Produto> produtos = produtoService.findAll();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.update(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarEstoqueBaixo(@RequestParam Integer quantidade) {
        return ResponseEntity.ok(produtoService.buscarProdutosComEstoqueBaixo(quantidade));
    }

    @GetMapping("/preco-ate")
    public ResponseEntity<List<Produto>> buscarPorPrecoAte(@RequestParam BigDecimal preco) {
        return ResponseEntity.ok(produtoService.buscarPorPrecoAte(preco));
    }
}
