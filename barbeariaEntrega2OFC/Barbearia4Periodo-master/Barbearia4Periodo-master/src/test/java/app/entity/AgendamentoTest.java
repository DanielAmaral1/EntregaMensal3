package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoTest {

    private Agendamento agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new Agendamento();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdAgendamentoGetterSetter() {
        Long id = 1L;
        agendamento.setId_agendamento(id);
        assertEquals(id, agendamento.getId_agendamento());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da data e hora")
    void testDataHoraGetterSetter() {
        LocalDateTime dataHora = LocalDateTime.now();
        agendamento.setDataHora(dataHora);
        assertEquals(dataHora, agendamento.getDataHora());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters das observações")
    void testObservacoesGetterSetter() {
        String observacoes = "Cliente preferencial";
        agendamento.setObservacoes(observacoes);
        assertEquals(observacoes, agendamento.getObservacoes());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação de agendamento com construtor padrão")
    void testConstrutorPadrao() {
        Agendamento novoAgendamento = new Agendamento();
        assertNotNull(novoAgendamento);
        assertNull(novoAgendamento.getId_agendamento());
        assertNull(novoAgendamento.getDataHora());
        assertNull(novoAgendamento.getObservacoes());
    }
}