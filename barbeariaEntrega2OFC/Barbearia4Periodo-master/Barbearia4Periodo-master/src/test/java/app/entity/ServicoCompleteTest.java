package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoCompleteTest {

    @Test
    void testServicoConstructorAndGetters() {
        Servico servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Corte de Cabelo");
        servico.setDescricao("Corte masculino tradicional");
        servico.setPreco(new java.math.BigDecimal("25.00"));
        servico.setDuracaoMinutos(30);

        assertEquals(1L, servico.getId_servico());
        assertEquals("Corte de Cabelo", servico.getNome());
        assertEquals("Corte masculino tradicional", servico.getDescricao());
        assertEquals(new java.math.BigDecimal("25.00"), servico.getPreco());
        assertEquals(30, servico.getDuracaoMinutos());
    }

    @Test
    void testServicoSetters() {
        Servico servico = new Servico();
        
        servico.setId_servico(2L);
        servico.setNome("Barba");
        servico.setDescricao("Aparar e modelar barba");
        servico.setPreco(new java.math.BigDecimal("15.00"));
        servico.setDuracaoMinutos(20);

        assertEquals(2L, servico.getId_servico());
        assertEquals("Barba", servico.getNome());
        assertEquals("Aparar e modelar barba", servico.getDescricao());
        assertEquals(new java.math.BigDecimal("15.00"), servico.getPreco());
        assertEquals(20, servico.getDuracaoMinutos());
    }

    @Test
    void testServicoEqualsAndHashCode() {
        Servico servico1 = new Servico();
        servico1.setId_servico(1L);
        servico1.setNome("Corte");

        Servico servico2 = new Servico();
        servico2.setId_servico(1L);
        servico2.setNome("Corte");

        assertEquals(servico1, servico2);
        assertEquals(servico1.hashCode(), servico2.hashCode());
    }

    @Test
    void testServicoToString() {
        Servico servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Corte de Cabelo");
        
        String toString = servico.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Corte de Cabelo") || toString.contains("1"));
    }
}