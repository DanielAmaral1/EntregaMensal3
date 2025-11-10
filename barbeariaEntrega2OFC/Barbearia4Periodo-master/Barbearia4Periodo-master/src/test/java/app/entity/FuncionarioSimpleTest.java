package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioSimpleTest {

    private FuncionarioSimple funcionarioSimple;

    @BeforeEach
    void setUp() {
        funcionarioSimple = new FuncionarioSimple();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdGetterSetter() {
        Long id = 1L;
        funcionarioSimple.setId_funcionario(id);
        assertEquals(id, funcionarioSimple.getId_funcionario());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Carlos";
        funcionarioSimple.setNome(nome);
        assertEquals(nome, funcionarioSimple.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação com construtor padrão")
    void testConstrutorPadrao() {
        FuncionarioSimple novo = new FuncionarioSimple();
        assertNotNull(novo);
    }
}