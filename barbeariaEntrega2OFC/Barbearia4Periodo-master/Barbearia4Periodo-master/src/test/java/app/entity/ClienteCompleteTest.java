package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteCompleteTest {

    @Test
    void testClienteConstructorAndGetters() {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setCelular("11-987654321");
        cliente.setIdade(30);

        assertEquals(1L, cliente.getId_cliente());
        assertEquals("João Silva", cliente.getNome());
        assertEquals("joao@email.com", cliente.getEmail());
        assertEquals("11-987654321", cliente.getCelular());
        assertEquals(30, cliente.getIdade());
    }

    @Test
    void testClienteSetters() {
        Cliente cliente = new Cliente();
        
        cliente.setId_cliente(2L);
        cliente.setNome("Maria Santos");
        cliente.setEmail("maria@email.com");
        cliente.setCelular("11-123456789");
        cliente.setIdade(25);

        assertEquals(2L, cliente.getId_cliente());
        assertEquals("Maria Santos", cliente.getNome());
        assertEquals("maria@email.com", cliente.getEmail());
        assertEquals("11-123456789", cliente.getCelular());
        assertEquals(25, cliente.getIdade());
    }

    @Test
    void testClienteEqualsAndHashCode() {
        Cliente cliente1 = new Cliente();
        cliente1.setId_cliente(1L);
        cliente1.setNome("João");

        Cliente cliente2 = new Cliente();
        cliente2.setId_cliente(1L);
        cliente2.setNome("João");

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }

    @Test
    void testClienteToString() {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("João Silva");
        
        String toString = cliente.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("João Silva") || toString.contains("1"));
    }
}