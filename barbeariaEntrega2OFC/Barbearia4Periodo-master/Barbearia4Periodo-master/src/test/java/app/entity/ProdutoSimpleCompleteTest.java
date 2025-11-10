package app.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoSimpleCompleteTest {

    @Test
    void testProdutoSimpleConstructorAndGetters() {
        ProdutoSimple produto = new ProdutoSimple();
        produto.setId_produto(1L);
        produto.setNome("Gel");
        produto.setPreco(new java.math.BigDecimal("12.50"));

        assertEquals(1L, produto.getId_produto());
        assertEquals("Gel", produto.getNome());
        assertEquals(new java.math.BigDecimal("12.50"), produto.getPreco());
    }

    @Test
    void testProdutoSimpleSetters() {
        ProdutoSimple produto = new ProdutoSimple();
        
        produto.setId_produto(2L);
        produto.setNome("Pomada");
        produto.setPreco(new java.math.BigDecimal("18.00"));

        assertEquals(2L, produto.getId_produto());
        assertEquals("Pomada", produto.getNome());
        assertEquals(new java.math.BigDecimal("18.00"), produto.getPreco());
    }

    @Test
    void testProdutoSimpleEqualsAndHashCode() {
        ProdutoSimple produto1 = new ProdutoSimple();
        produto1.setId_produto(1L);
        produto1.setNome("Gel");

        ProdutoSimple produto2 = new ProdutoSimple();
        produto2.setId_produto(1L);
        produto2.setNome("Gel");

        assertEquals(produto1, produto2);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    void testProdutoSimpleToString() {
        ProdutoSimple produto = new ProdutoSimple();
        produto.setId_produto(1L);
        produto.setNome("Gel");
        
        String toString = produto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Gel") || toString.contains("1"));
    }
}