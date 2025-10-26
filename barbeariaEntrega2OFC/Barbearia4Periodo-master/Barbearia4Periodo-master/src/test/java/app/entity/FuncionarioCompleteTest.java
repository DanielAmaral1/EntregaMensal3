package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioCompleteTest {

    @Test
    void testFuncionarioConstructorAndGetters() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos Silva");
        funcionario.setTelefone("11-999888777");
        funcionario.setEndereco("Rua A, 123");

        assertEquals(1L, funcionario.getId_funcionario());
        assertEquals("Carlos Silva", funcionario.getNome());
        assertEquals("11-999888777", funcionario.getTelefone());
        assertEquals("Rua A, 123", funcionario.getEndereco());
    }

    @Test
    void testFuncionarioSetters() {
        Funcionario funcionario = new Funcionario();
        
        funcionario.setId_funcionario(2L);
        funcionario.setNome("Ana Costa");
        funcionario.setTelefone("11-888777666");
        funcionario.setEndereco("Rua B, 456");

        assertEquals(2L, funcionario.getId_funcionario());
        assertEquals("Ana Costa", funcionario.getNome());
        assertEquals("11-888777666", funcionario.getTelefone());
        assertEquals("Rua B, 456", funcionario.getEndereco());
    }

    @Test
    void testFuncionarioEqualsAndHashCode() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setId_funcionario(1L);
        funcionario1.setNome("Carlos");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setId_funcionario(1L);
        funcionario2.setNome("Carlos");

        assertEquals(funcionario1, funcionario2);
        assertEquals(funcionario1.hashCode(), funcionario2.hashCode());
    }

    @Test
    void testFuncionarioToString() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos Silva");
        
        String toString = funcionario.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Carlos Silva") || toString.contains("1"));
    }
}