package app.service;

import app.entity.Produto;
import app.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceCompleteTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Condicionador");
        produto.setDescricao("Condicionador hidratante");
        produto.setPreco(new BigDecimal("19.90"));
        produto.setQuantidadeEstoque(15);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar todos os produtos")
    void testFindAllSuccess() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));
        
        List<Produto> resultado = produtoService.findAll();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar produto por ID")
    void testFindByIdSuccess() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        
        Optional<Produto> resultado = produtoService.findById(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals("Condicionador", resultado.get().getNome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por nome")
    void testBuscarPorNomeSuccess() {
        when(produtoRepository.findByNomeContaining("Condicionador")).thenReturn(Arrays.asList(produto));
        
        List<Produto> resultado = produtoService.buscarPorNome("Condicionador");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoRepository, times(1)).findByNomeContaining("Condicionador");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por preço até valor")
    void testBuscarPorPrecoAteSuccess() {
        BigDecimal preco = new BigDecimal("20.00");
        when(produtoRepository.findByPrecoLessThanEqual(preco)).thenReturn(Arrays.asList(produto));
        
        List<Produto> resultado = produtoService.buscarPorPrecoAte(preco);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoRepository, times(1)).findByPrecoLessThanEqual(preco);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao deletar produto sem estoque")
    void testDeleteProdutoSemEstoque() {
        produto.setQuantidadeEstoque(0);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).deleteById(1L);
        
        produtoService.deleteById(1L);
        
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao atualizar produto")
    void testUpdateProdutoSuccess() {
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setNome("Condicionador Premium");
        produtoAtualizado.setDescricao("Condicionador premium hidratante");
        produtoAtualizado.setPreco(new BigDecimal("29.90"));
        produtoAtualizado.setQuantidadeEstoque(20);
        
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoAtualizado);
        
        Produto resultado = produtoService.update(1L, produtoAtualizado);
        
        assertNotNull(resultado);
        assertEquals("Condicionador Premium", resultado.getNome());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por produtos com estoque baixo com resultado")
    void testBuscarProdutosEstoqueBaixoComResultado() {
        produto.setQuantidadeEstoque(3);
        when(produtoRepository.buscarProdutosComEstoqueBaixo(5)).thenReturn(Arrays.asList(produto));
        
        List<Produto> resultado = produtoService.buscarProdutosComEstoqueBaixo(5);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(0).getQuantidadeEstoque());
        verify(produtoRepository, times(1)).buscarProdutosComEstoqueBaixo(5);
    }
}