package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServicoSimpleTest {

    private ServicoSimple servicoSimple;

    @BeforeEach
    void setUp() {
        servicoSimple = new ServicoSimple();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdGetterSetter() {
        Long id = 1L;
        servicoSimple.setId_servico(id);
        assertEquals(id, servicoSimple.getId_servico());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Serviço";
        servicoSimple.setNome(nome);
        assertEquals(nome, servicoSimple.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do preço")
    void testPrecoGetterSetter() {
        BigDecimal preco = new BigDecimal("25.00");
        servicoSimple.setPreco(preco);
        assertEquals(preco, servicoSimple.getPreco());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação com construtor padrão")
    void testConstrutorPadrao() {
        ServicoSimple novo = new ServicoSimple();
        assertNotNull(novo);
    }
}