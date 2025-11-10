package app.controller;

import app.entity.Servico;
import app.service.ServicoService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicoController.class)
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicoService servicoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Servico servico;

    @BeforeEach
    void setUp() {
        servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Corte Masculino");
        servico.setDescricao("Corte tradicional");
        servico.setPreco(new BigDecimal("25.00"));
        servico.setDuracaoMinutos(30);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar serviço")
    void testCreateServico() throws Exception {
        when(servicoService.save(any(Servico.class))).thenReturn(servico);

        mockMvc.perform(post("/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servico)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar todos os serviços")
    void testGetAllServicos() throws Exception {
        List<Servico> servicos = Arrays.asList(servico);
        when(servicoService.findAll()).thenReturn(servicos);

        mockMvc.perform(get("/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por ID com sucesso")
    void testGetServicoById() throws Exception {
        when(servicoService.findById(1L)).thenReturn(servico);

        mockMvc.perform(get("/servicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao atualizar serviço")
    void testUpdateServico() throws Exception {
        when(servicoService.update(anyLong(), any(Servico.class))).thenReturn(servico);

        mockMvc.perform(put("/servicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servico)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao deletar serviço")
    void testDeleteServico() throws Exception {
        mockMvc.perform(delete("/servicos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome com resultados")
    void testBuscarPorNome() throws Exception {
        List<Servico> servicos = Arrays.asList(servico);
        when(servicoService.buscarPorNome("Corte")).thenReturn(servicos);

        mockMvc.perform(get("/servicos/buscar")
                .param("nome", "Corte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por preço máximo")
    void testBuscarPorPrecoAte() throws Exception {
        List<Servico> servicos = Arrays.asList(servico);
        when(servicoService.buscarPorPrecoAte(new BigDecimal("30.00"))).thenReturn(servicos);

        mockMvc.perform(get("/servicos/preco-ate")
                .param("preco", "30.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Corte Masculino"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca quantidade de serviços")
    void testBuscarQtdServico() throws Exception {
        when(servicoService.buscarQtdServico()).thenReturn(5L);

        mockMvc.perform(get("/servicos/qtd"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}