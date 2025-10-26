package app.controller;

import app.entity.FuncionarioSimple;
import app.entity.ProdutoSimple;
import app.entity.ServicoSimple;
import app.repository.FuncionarioSimpleRepository;
import app.repository.ProdutoSimpleRepository;
import app.repository.ServicoSimpleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestSimpleController.class)
class TestSimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioSimpleRepository funcionarioRepository;

    @MockBean
    private ProdutoSimpleRepository produtoRepository;

    @MockBean
    private ServicoSimpleRepository servicoRepository;

    private FuncionarioSimple funcionario;
    private ProdutoSimple produto;
    private ServicoSimple servico;

    @BeforeEach
    void setUp() {
        funcionario = new FuncionarioSimple();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos");

        produto = new ProdutoSimple();
        produto.setId_produto(1L);
        produto.setNome("Shampoo");
        produto.setPreco(new BigDecimal("25.90"));

        servico = new ServicoSimple();
        servico.setId_servico(1L);
        servico.setNome("Corte");
        servico.setPreco(new BigDecimal("30.00"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar funcionários de teste")
    void testGetFuncionarios() throws Exception {
        mockMvc.perform(get("/test-simple/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar produtos de teste")
    void testGetProdutos() throws Exception {
        mockMvc.perform(get("/test-simple/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar serviços de teste")
    void testGetServicos() throws Exception {
        mockMvc.perform(get("/test-simple/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Serviço Teste"));
    }
}