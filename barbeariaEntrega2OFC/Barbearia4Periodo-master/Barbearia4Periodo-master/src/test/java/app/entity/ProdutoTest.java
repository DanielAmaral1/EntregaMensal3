package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdProdutoGetterSetter() {
        Long id = 1L;
        produto.setId_produto(id);
        assertEquals(id, produto.getId_produto());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Shampoo";
        produto.setNome(nome);
        assertEquals(nome, produto.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da descrição")
    void testDescricaoGetterSetter() {
        String descricao = "Shampoo anticaspa";
        produto.setDescricao(descricao);
        assertEquals(descricao, produto.getDescricao());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do preço")
    void testPrecoGetterSetter() {
        BigDecimal preco = new BigDecimal("25.90");
        produto.setPreco(preco);
        assertEquals(preco, produto.getPreco());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da quantidade em estoque")
    void testQuantidadeEstoqueGetterSetter() {
        Integer quantidade = 10;
        produto.setQuantidadeEstoque(quantidade);
        assertEquals(quantidade, produto.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação de produto com construtor padrão")
    void testConstrutorPadrao() {
        Produto novoProduto = new Produto();
        assertNotNull(novoProduto);
        assertNull(novoProduto.getId_produto());
        assertNull(novoProduto.getNome());
        assertNull(novoProduto.getDescricao());
        assertNull(novoProduto.getPreco());
        assertNull(novoProduto.getQuantidadeEstoque());
    }
}