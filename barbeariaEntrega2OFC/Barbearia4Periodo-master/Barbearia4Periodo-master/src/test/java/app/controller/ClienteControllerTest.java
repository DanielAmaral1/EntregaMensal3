package app.controller;

import app.entity.Cliente;
import app.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setCelular("11-987654321");
        cliente.setIdade(30);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar todos os clientes")
    void testGetAllClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.findAll()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar cliente")
    void testCreateCliente() throws Exception {
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

//Identificacao de tipo de teste/cenario
//teste de integracao (repositories mockados)
//clareza, organizacao e estrutura dos testes
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por ID com sucesso")
    void testFindClienteById() throws Exception {
        when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de pesquisa global com resultados")
    void testPesquisarGlobal() throws Exception {
        List<Cliente> resultados = Arrays.asList(cliente);
        when(clienteService.pesquisarGlobal("João")).thenReturn(resultados);

        mockMvc.perform(get("/clientes/pesquisar")
                .param("termo", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao atualizar cliente")
    void testUpdateCliente() throws Exception {
        when(clienteService.update(anyLong(), any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao deletar cliente")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome com resultados")
    void testBuscarPorNome() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.buscarPorNome("João")).thenReturn(clientes);

        mockMvc.perform(get("/clientes/by-nome")
                .param("nome", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por idade com resultados")
    void testBuscarPorIdade() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.buscarPorIdade(30)).thenReturn(clientes);

        mockMvc.perform(get("/clientes/by-idade")
                .param("idade", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idade").value(30));
    }

//cobertura de casos limites e excecoes
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de lista vazia de clientes")
    void testGetAllClientesEmpty() throws Exception {
        when(clienteService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de pesquisa global sem resultados")
    void testPesquisarGlobalSemResultados() throws Exception {
        when(clienteService.pesquisarGlobal("inexistente")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/clientes/pesquisar")
                .param("termo", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome sem resultados")
    void testBuscarPorNomeSemResultados() throws Exception {
        when(clienteService.buscarPorNome("inexistente")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/clientes/by-nome")
                .param("nome", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}