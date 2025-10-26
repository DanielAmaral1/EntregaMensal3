package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoSimpleTest {

    private ProdutoSimple produtoSimple;

    @BeforeEach
    void setUp() {
        produtoSimple = new ProdutoSimple();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdGetterSetter() {
        Long id = 1L;
        produtoSimple.setId_produto(id);
        assertEquals(id, produtoSimple.getId_produto());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Produto";
        produtoSimple.setNome(nome);
        assertEquals(nome, produtoSimple.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do preço")
    void testPrecoGetterSetter() {
        BigDecimal preco = new BigDecimal("10.00");
        produtoSimple.setPreco(preco);
        assertEquals(preco, produtoSimple.getPreco());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação com construtor padrão")
    void testConstrutorPadrao() {
        ProdutoSimple novo = new ProdutoSimple();
        assertNotNull(novo);
    }
}