package app.controller;

import app.entity.Funcionario;
import app.service.FuncionarioService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos Silva");
        funcionario.setTelefone("11-999888777");
        funcionario.setEndereco("Rua A, 123");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar todos os funcionários")
    void testListarTodos() throws Exception {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(funcionarioService.findAll()).thenReturn(funcionarios);

        mockMvc.perform(get("/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por ID com sucesso")
    void testBuscarPorId() throws Exception {
        when(funcionarioService.findById(1L)).thenReturn(funcionario);

        mockMvc.perform(get("/funcionarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar funcionário")
    void testAdicionar() throws Exception {
        when(funcionarioService.save(any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(post("/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao atualizar funcionário")
    void testAtualizar() throws Exception {
        when(funcionarioService.update(anyLong(), any(Funcionario.class))).thenReturn(funcionario);

        mockMvc.perform(put("/funcionarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao remover funcionário")
    void testRemover() throws Exception {
        mockMvc.perform(delete("/funcionarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome com resultados")
    void testBuscarPorNome() throws Exception {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(funcionarioService.buscarPorNome("Carlos")).thenReturn(funcionarios);

        mockMvc.perform(get("/funcionarios/by-nome")
                .param("nome", "Carlos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Carlos Silva"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por telefone com resultados")
    void testBuscarPorTelefone() throws Exception {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(funcionarioService.buscarPorTelefone("11-999888777")).thenReturn(funcionarios);

        mockMvc.perform(get("/funcionarios/by-telefone")
                .param("telefone", "11-999888777"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].telefone").value("11-999888777"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome sem resultados")
    void testBuscarPorNomeSemResultados() throws Exception {
        when(funcionarioService.buscarPorNome("inexistente")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/funcionarios/by-nome")
                .param("nome", "inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por telefone sem resultados")
    void testBuscarPorTelefoneSemResultados() throws Exception {
        when(funcionarioService.buscarPorTelefone("999")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/funcionarios/by-telefone")
                .param("telefone", "999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}