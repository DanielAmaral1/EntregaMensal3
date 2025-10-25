package app.controller;

import app.entity.*;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private FuncionarioService funcionarioService;
    
    @Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ServicoService servicoService;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> testStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            List<Cliente> clientes = clienteService.findAll();
            status.put("clientes_count", clientes.size());
            status.put("clientes_data", clientes);
        } catch (Exception e) {
            status.put("clientes", "ERRO: " + e.getMessage());
        }
        
        try {
            List<Funcionario> funcionarios = funcionarioService.findAll();
            status.put("funcionarios_count", funcionarios.size());
            status.put("funcionarios_data", funcionarios);
        } catch (Exception e) {
            status.put("funcionarios", "ERRO: " + e.getMessage());
        }
        
        try {
            List<Produto> produtos = produtoService.findAll();
            status.put("produtos_count", produtos.size());
            status.put("produtos_data", produtos);
        } catch (Exception e) {
            status.put("produtos", "ERRO: " + e.getMessage());
        }
        
        try {
            List<Servico> servicos = servicoService.findAll();
            status.put("servicos_count", servicos.size());
            status.put("servicos_data", servicos);
        } catch (Exception e) {
            status.put("servicos", "ERRO: " + e.getMessage());
        }
        
        return ResponseEntity.ok(status);
    }

    @PostMapping("/create-sample")
    public ResponseEntity<String> createSampleData() {
        try {
            // Criar funcionário
            Funcionario func = new Funcionario();
            func.setNome("João Barbeiro");
            func.setTelefone("11-999888777");
            func.setEndereco("Rua Teste, 123");
            funcionarioService.save(func);
            
            // Criar produto
            Produto prod = new Produto();
            prod.setNome("Shampoo Teste");
            prod.setDescricao("Produto de teste");
            prod.setPreco(new BigDecimal("25.90"));
            prod.setQuantidadeEstoque(10);
            produtoService.save(prod);
            
            return ResponseEntity.ok("Dados de teste criados com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}