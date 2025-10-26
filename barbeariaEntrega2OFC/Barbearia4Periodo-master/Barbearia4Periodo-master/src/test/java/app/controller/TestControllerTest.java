package app.controller;

import app.entity.Cliente;
import app.entity.Funcionario;
import app.entity.Produto;
import app.entity.Servico;
import app.service.ClienteService;
import app.service.FuncionarioService;
import app.service.ProdutoService;
import app.service.ServicoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ServicoService servicoService;

    private Cliente cliente;
    private Funcionario funcionario;
    private Produto produto;
    private Servico servico;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("João");

        funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos");

        produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Shampoo");
        produto.setPreco(new BigDecimal("25.90"));

        servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Corte");
        servico.setPreco(new BigDecimal("30.00"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de teste de status geral do sistema")
    void testStatus() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        List<Produto> produtos = Arrays.asList(produto);
        List<Servico> servicos = Arrays.asList(servico);

        when(clienteService.findAll()).thenReturn(clientes);
        when(funcionarioService.findAll()).thenReturn(funcionarios);
        when(produtoService.findAll()).thenReturn(produtos);
        when(servicoService.findAll()).thenReturn(servicos);

        mockMvc.perform(get("/test/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientes_count").value(1))
                .andExpect(jsonPath("$.funcionarios_count").value(1))
                .andExpect(jsonPath("$.produtos_count").value(1))
                .andExpect(jsonPath("$.servicos_count").value(1));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de criação de dados de exemplo")
    void testCreateSampleData() throws Exception {
        when(funcionarioService.save(any(Funcionario.class))).thenReturn(funcionario);
        when(produtoService.save(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/test/create-sample"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dados de teste criados com sucesso!"));
    }
}