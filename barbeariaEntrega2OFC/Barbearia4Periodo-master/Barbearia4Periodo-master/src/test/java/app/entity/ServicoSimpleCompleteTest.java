package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServicoSimpleCompleteTest {

    @Test
    void testServicoSimpleConstructorAndGetters() {
        ServicoSimple servico = new ServicoSimple();
        servico.setId_servico(1L);
        servico.setNome("Corte");
        servico.setPreco(new java.math.BigDecimal("20.00"));

        assertEquals(1L, servico.getId_servico());
        assertEquals("Corte", servico.getNome());
        assertEquals(new java.math.BigDecimal("20.00"), servico.getPreco());
    }

    @Test
    void testServicoSimpleSetters() {
        ServicoSimple servico = new ServicoSimple();
        
        servico.setId_servico(2L);
        servico.setNome("Barba");
        servico.setPreco(new java.math.BigDecimal("15.00"));

        assertEquals(2L, servico.getId_servico());
        assertEquals("Barba", servico.getNome());
        assertEquals(new java.math.BigDecimal("15.00"), servico.getPreco());
    }

    @Test
    void testServicoSimpleEqualsAndHashCode() {
        ServicoSimple servico1 = new ServicoSimple();
        servico1.setId_servico(1L);
        servico1.setNome("Corte");

        ServicoSimple servico2 = new ServicoSimple();
        servico2.setId_servico(1L);
        servico2.setNome("Corte");

        assertEquals(servico1, servico2);
        assertEquals(servico1.hashCode(), servico2.hashCode());
    }

    @Test
    void testServicoSimpleToString() {
        ServicoSimple servico = new ServicoSimple();
        servico.setId_servico(1L);
        servico.setNome("Corte");
        
        String toString = servico.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Corte") || toString.contains("1"));
    }
}