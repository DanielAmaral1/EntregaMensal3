package app.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AgendamentoCompleteTest {

    @Test
    void testAgendamentoConstructorAndGetters() {
        Agendamento agendamento = new Agendamento();
        LocalDateTime dataHora = LocalDateTime.now();
        Cliente cliente = new Cliente();
        Funcionario funcionario = new Funcionario();
        Servico servico = new Servico();
        List<Produto> produtos = new ArrayList<>();

        agendamento.setId_agendamento(1L);
        agendamento.setDataHora(dataHora);
        agendamento.setObservacoes("Teste");
        agendamento.setCliente(cliente);
        agendamento.setFuncionario(funcionario);
        agendamento.setServico(servico);
        agendamento.setProdutos(produtos);

        assertEquals(1L, agendamento.getId_agendamento());
        assertEquals(dataHora, agendamento.getDataHora());
        assertEquals("Teste", agendamento.getObservacoes());
        assertEquals(cliente, agendamento.getCliente());
        assertEquals(funcionario, agendamento.getFuncionario());
        assertEquals(servico, agendamento.getServico());
        assertEquals(produtos, agendamento.getProdutos());
    }

    @Test
    void testAgendamentoSetters() {
        Agendamento agendamento = new Agendamento();
        LocalDateTime novaData = LocalDateTime.of(2024, 12, 25, 10, 30);
        
        agendamento.setId_agendamento(2L);
        agendamento.setDataHora(novaData);
        agendamento.setObservacoes("Nova observação");

        assertEquals(2L, agendamento.getId_agendamento());
        assertEquals(novaData, agendamento.getDataHora());
        assertEquals("Nova observação", agendamento.getObservacoes());
    }

    @Test
    void testAgendamentoEqualsAndHashCode() {
        Agendamento agendamento1 = new Agendamento();
        agendamento1.setId_agendamento(1L);
        agendamento1.setObservacoes("Teste");

        Agendamento agendamento2 = new Agendamento();
        agendamento2.setId_agendamento(1L);
        agendamento2.setObservacoes("Teste");

        assertEquals(agendamento1, agendamento2);
        assertEquals(agendamento1.hashCode(), agendamento2.hashCode());
    }

    @Test
    void testAgendamentoToString() {
        Agendamento agendamento = new Agendamento();
        agendamento.setId_agendamento(1L);
        agendamento.setObservacoes("Teste");
        
        String toString = agendamento.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Teste") || toString.contains("1"));
    }
}