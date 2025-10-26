package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoCompleteTest {

    @Test
    void testProdutoConstructorAndGetters() {
        Produto produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Shampoo");
        produto.setDescricao("Shampoo anticaspa");
        produto.setPreco(new java.math.BigDecimal("25.50"));
        produto.setQuantidadeEstoque(100);

        assertEquals(1L, produto.getId_produto());
        assertEquals("Shampoo", produto.getNome());
        assertEquals("Shampoo anticaspa", produto.getDescricao());
        assertEquals(new java.math.BigDecimal("25.50"), produto.getPreco());
        assertEquals(100, produto.getQuantidadeEstoque());
    }

    @Test
    void testProdutoSetters() {
        Produto produto = new Produto();
        
        produto.setId_produto(2L);
        produto.setNome("Condicionador");
        produto.setDescricao("Condicionador hidratante");
        produto.setPreco(new java.math.BigDecimal("30.00"));
        produto.setQuantidadeEstoque(50);

        assertEquals(2L, produto.getId_produto());
        assertEquals("Condicionador", produto.getNome());
        assertEquals("Condicionador hidratante", produto.getDescricao());
        assertEquals(new java.math.BigDecimal("30.00"), produto.getPreco());
        assertEquals(50, produto.getQuantidadeEstoque());
    }

    @Test
    void testProdutoEqualsAndHashCode() {
        Produto produto1 = new Produto();
        produto1.setId_produto(1L);
        produto1.setNome("Shampoo");

        Produto produto2 = new Produto();
        produto2.setId_produto(1L);
        produto2.setNome("Shampoo");

        assertEquals(produto1, produto2);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    void testProdutoToString() {
        Produto produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Shampoo");
        
        String toString = produto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Shampoo") || toString.contains("1"));
    }
}