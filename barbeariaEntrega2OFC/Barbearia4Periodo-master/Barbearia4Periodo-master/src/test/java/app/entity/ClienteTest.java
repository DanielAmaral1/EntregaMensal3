package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdClienteGetterSetter() {
        Long id = 1L;
        cliente.setId_cliente(id);
        assertEquals(id, cliente.getId_cliente());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "João Silva";
        cliente.setNome(nome);
        assertEquals(nome, cliente.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do email")
    void testEmailGetterSetter() {
        String email = "joao@email.com";
        cliente.setEmail(email);
        assertEquals(email, cliente.getEmail());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do celular")
    void testCelularGetterSetter() {
        String celular = "11-999888777";
        cliente.setCelular(celular);
        assertEquals(celular, cliente.getCelular());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da idade")
    void testIdadeGetterSetter() {
        Integer idade = 30;
        cliente.setIdade(idade);
        assertEquals(idade, cliente.getIdade());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação de cliente com construtor padrão")
    void testConstrutorPadrao() {
        Cliente novoCliente = new Cliente();
        assertNotNull(novoCliente);
        assertNull(novoCliente.getId_cliente());
        assertNull(novoCliente.getNome());
        assertNull(novoCliente.getEmail());
        assertNull(novoCliente.getCelular());
        assertNull(novoCliente.getIdade());
    }
}