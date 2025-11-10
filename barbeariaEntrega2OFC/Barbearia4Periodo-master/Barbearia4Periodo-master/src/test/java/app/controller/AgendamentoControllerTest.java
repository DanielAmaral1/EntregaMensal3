package app.controller;

import app.entity.Agendamento;
import app.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Agendamento agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new Agendamento();
        agendamento.setId_agendamento(1L);
        agendamento.setDataHora(LocalDateTime.now());
        agendamento.setObservacoes("Teste");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar agendamento")
    void testCreate() throws Exception {
        when(agendamentoService.save(any(Agendamento.class))).thenReturn(agendamento);

        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendamento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.observacoes").value("Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar todos os agendamentos")
    void testGetAll() throws Exception {
        List<Agendamento> agendamentos = Arrays.asList(agendamento);
        when(agendamentoService.findAll()).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].observacoes").value("Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por ID com sucesso")
    void testFindById() throws Exception {
        when(agendamentoService.findById(1L)).thenReturn(agendamento);

        mockMvc.perform(get("/agendamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.observacoes").value("Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao atualizar agendamento")
    void testUpdate() throws Exception {
        when(agendamentoService.update(anyLong(), any(Agendamento.class))).thenReturn(agendamento);

        mockMvc.perform(put("/agendamentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendamento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.observacoes").value("Teste"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao deletar agendamento")
    void testDelete() throws Exception {
        mockMvc.perform(delete("/agendamentos/1"))
                .andExpect(status().isNoContent());
    }
}