package app.controller;

import app.entity.FuncionarioSimple;
import app.entity.ProdutoSimple;
import app.entity.ServicoSimple;
import app.repository.FuncionarioSimpleRepository;
import app.repository.ProdutoSimpleRepository;
import app.repository.ServicoSimpleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SimpleController.class)
class SimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioSimpleRepository funcionarioRepository;

    @MockBean
    private ProdutoSimpleRepository produtoRepository;

    @MockBean
    private ServicoSimpleRepository servicoRepository;

    @Autowired
    private ObjectMapper objectMapper;

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
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar funcionários simples")
    void testGetFuncionarios() throws Exception {
        List<FuncionarioSimple> funcionarios = Arrays.asList(funcionario);
        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        mockMvc.perform(get("/simple/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Carlos"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de erro interno do servidor para funcionários")
    void testGetFuncionariosError() throws Exception {
        when(funcionarioRepository.findAll()).thenThrow(new RuntimeException("Erro interno"));
        
        mockMvc.perform(get("/simple/funcionarios"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar produtos simples")
    void testGetProdutos() throws Exception {
        List<ProdutoSimple> produtos = Arrays.asList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        mockMvc.perform(get("/simple/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de erro interno do servidor para produtos")
    void testGetProdutosError() throws Exception {
        when(produtoRepository.findAll()).thenThrow(new RuntimeException("Erro interno"));
        
        mockMvc.perform(get("/simple/produtos"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar serviços simples")
    void testGetServicos() throws Exception {
        List<ServicoSimple> servicos = Arrays.asList(servico);
        when(servicoRepository.findAll()).thenReturn(servicos);

        mockMvc.perform(get("/simple/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de erro interno do servidor para serviços")
    void testGetServicosError() throws Exception {
        when(servicoRepository.findAll()).thenThrow(new RuntimeException("Erro interno"));
        
        mockMvc.perform(get("/simple/servicos"))
                .andExpect(status().isInternalServerError());
    }
}