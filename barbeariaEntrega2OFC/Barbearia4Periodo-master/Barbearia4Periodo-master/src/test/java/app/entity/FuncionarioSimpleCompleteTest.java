package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioSimpleCompleteTest {

    @Test
    void testFuncionarioSimpleConstructorAndGetters() {
        FuncionarioSimple funcionario = new FuncionarioSimple();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("João");

        assertEquals(1L, funcionario.getId_funcionario());
        assertEquals("João", funcionario.getNome());
    }

    @Test
    void testFuncionarioSimpleSetters() {
        FuncionarioSimple funcionario = new FuncionarioSimple();
        
        funcionario.setId_funcionario(2L);
        funcionario.setNome("Maria");

        assertEquals(2L, funcionario.getId_funcionario());
        assertEquals("Maria", funcionario.getNome());
    }

    @Test
    void testFuncionarioSimpleEqualsAndHashCode() {
        FuncionarioSimple funcionario1 = new FuncionarioSimple();
        funcionario1.setId_funcionario(1L);
        funcionario1.setNome("João");

        FuncionarioSimple funcionario2 = new FuncionarioSimple();
        funcionario2.setId_funcionario(1L);
        funcionario2.setNome("João");

        assertEquals(funcionario1, funcionario2);
        assertEquals(funcionario1.hashCode(), funcionario2.hashCode());
    }

    @Test
    void testFuncionarioSimpleToString() {
        FuncionarioSimple funcionario = new FuncionarioSimple();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("João");
        
        String toString = funcionario.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("João") || toString.contains("1"));
    }
}