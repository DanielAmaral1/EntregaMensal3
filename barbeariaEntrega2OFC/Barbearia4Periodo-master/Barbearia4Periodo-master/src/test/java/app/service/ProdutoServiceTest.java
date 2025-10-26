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
import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Shampoo Premium");
        produto.setDescricao("Shampoo para cabelos");
        produto.setPreco(new BigDecimal("29.90"));
        produto.setQuantidadeEstoque(10);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar produto com estoque válido")
    void testSaveProdutoSuccess() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        
        Produto resultado = produtoService.save(produto);
        
        assertNotNull(resultado);
        assertEquals("Shampoo Premium", resultado.getNome());
        assertEquals(10, resultado.getQuantidadeEstoque());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário com erro de validation de estoque negativo")
    void testSaveProdutoEstoqueNegativo() {
        produto.setQuantidadeEstoque(-5);
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> {
            Produto p = invocation.getArgument(0);
            if (p.getQuantidadeEstoque() <= 0) {
                p.setQuantidadeEstoque(0);
            }
            return p;
        });
        
        Produto resultado = produtoService.save(produto);
        
        assertEquals(0, resultado.getQuantidadeEstoque());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao deletar produto com estoque")
    void testDeleteProdutoComEstoque() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.deleteById(1L);
        });
        
        assertEquals("Não é possível excluir produto com estoque disponível", exception.getMessage());
        verify(produtoRepository, never()).deleteById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por produtos com estoque baixo")
    void testBuscarProdutosEstoqueBaixo() {
        List<Produto> produtosBaixoEstoque = Arrays.asList(produto);
        when(produtoRepository.buscarProdutosComEstoqueBaixo(5)).thenReturn(produtosBaixoEstoque);
        
        List<Produto> resultado = produtoService.buscarProdutosComEstoqueBaixo(5);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(produtoRepository, times(1)).buscarProdutosComEstoqueBaixo(5);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar produto por ID inexistente")
    void testFindByIdNotFound() {
        when(produtoRepository.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Produto> resultado = produtoService.findById(999L);
        
        assertFalse(resultado.isPresent());
        verify(produtoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao atualizar produto inexistente")
    void testUpdateProdutoNotFound() {
        when(produtoRepository.findById(999L)).thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.update(999L, produto);
        });
        
        assertEquals("Produto não encontrado com id: 999", exception.getMessage());
        verify(produtoRepository, times(1)).findById(999L);
        verify(produtoRepository, never()).save(any(Produto.class));
    }



    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por preço sem resultados")
    void testBuscarPorPrecoAteEmpty() {
        BigDecimal preco = new BigDecimal("1.00");
        when(produtoRepository.findByPrecoLessThanEqual(preco)).thenReturn(Arrays.asList());
        
        List<Produto> resultado = produtoService.buscarPorPrecoAte(preco);
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepository, times(1)).findByPrecoLessThanEqual(preco);
    }
}