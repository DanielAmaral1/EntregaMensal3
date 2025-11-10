package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdFuncionarioGetterSetter() {
        Long id = 1L;
        funcionario.setId_funcionario(id);
        assertEquals(id, funcionario.getId_funcionario());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Carlos Silva";
        funcionario.setNome(nome);
        assertEquals(nome, funcionario.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do telefone")
    void testTelefoneGetterSetter() {
        String telefone = "11-999888777";
        funcionario.setTelefone(telefone);
        assertEquals(telefone, funcionario.getTelefone());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do endereço")
    void testEnderecoGetterSetter() {
        String endereco = "Rua A, 123";
        funcionario.setEndereco(endereco);
        assertEquals(endereco, funcionario.getEndereco());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação de funcionário com construtor padrão")
    void testConstrutorPadrao() {
        Funcionario novoFuncionario = new Funcionario();
        assertNotNull(novoFuncionario);
        assertNull(novoFuncionario.getId_funcionario());
        assertNull(novoFuncionario.getNome());
        assertNull(novoFuncionario.getTelefone());
        assertNull(novoFuncionario.getEndereco());
    }
}