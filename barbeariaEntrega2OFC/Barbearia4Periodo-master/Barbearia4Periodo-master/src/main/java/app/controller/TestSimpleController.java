package app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/test-simple")
public class TestSimpleController {

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Map<String, Object>>> getFuncionarios() {
        List<Map<String, Object>> funcionarios = new ArrayList<>();
        Map<String, Object> func = new HashMap<>();
        func.put("id_funcionario", 1L);
        func.put("nome", "João Teste");
        func.put("telefone", "11-999999999");
        func.put("endereco", "Rua Teste, 123");
        funcionarios.add(func);
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<Map<String, Object>>> getProdutos() {
        List<Map<String, Object>> produtos = new ArrayList<>();
        Map<String, Object> prod = new HashMap<>();
        prod.put("id_produto", 1L);
        prod.put("nome", "Produto Teste");
        prod.put("descricao", "Descrição teste");
        prod.put("preco", 25.90);
        prod.put("quantidadeEstoque", 10);
        produtos.add(prod);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/servicos")
    public ResponseEntity<List<Map<String, Object>>> getServicos() {
        List<Map<String, Object>> servicos = new ArrayList<>();
        Map<String, Object> serv = new HashMap<>();
        serv.put("id_servico", 1L);
        serv.put("nome", "Serviço Teste");
        serv.put("descricao", "Descrição teste");
        serv.put("preco", 30.00);
        serv.put("duracaoMinutos", 45);
        servicos.add(serv);
        return ResponseEntity.ok(servicos);
    }
}