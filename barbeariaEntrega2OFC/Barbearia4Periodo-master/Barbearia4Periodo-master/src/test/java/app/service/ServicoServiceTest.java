package app.service;

import app.entity.Servico;
import app.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    private Servico servico;

    @BeforeEach
    void setUp() {
        servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Corte Masculino");
        servico.setDescricao("Corte tradicional");
        servico.setPreco(new BigDecimal("25.00"));
        servico.setDuracaoMinutos(30);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar serviço")
    void testSaveServicoSuccess() {
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);
        
        Servico resultado = servicoService.save(servico);
        
        assertNotNull(resultado);
        assertEquals("Corte Masculino", resultado.getNome());
        assertEquals(new BigDecimal("25.00"), resultado.getPreco());
        verify(servicoRepository, times(1)).save(servico);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por preço máximo")
    void testBuscarPorPrecoAte() {
        List<Servico> servicos = Arrays.asList(servico);
        BigDecimal precoMaximo = new BigDecimal("30.00");
        when(servicoRepository.findByPrecoLessThanEqual(precoMaximo)).thenReturn(servicos);
        
        List<Servico> resultado = servicoService.buscarPorPrecoAte(precoMaximo);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(servicoRepository, times(1)).findByPrecoLessThanEqual(precoMaximo);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao deletar serviço inexistente")
    void testDeleteServicoNotFound() {
        when(servicoRepository.existsById(999L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            servicoService.deleteById(999L);
        });
        
        assertEquals("Servico not found with id: 999", exception.getMessage());
        verify(servicoRepository, never()).deleteById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de contagem de serviços")
    void testBuscarQtdServico() {
        when(servicoRepository.buscarQtdServico()).thenReturn(5L);
        
        Long quantidade = servicoService.buscarQtdServico();
        
        assertEquals(5L, quantidade);
        verify(servicoRepository, times(1)).buscarQtdServico();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar serviço por ID inexistente")
    void testFindByIdNotFound() {
        when(servicoRepository.findById(999L)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            servicoService.findById(999L);
        });
        
        assertEquals("Servico not found with id: 999", exception.getMessage());
        verify(servicoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao atualizar serviço inexistente")
    void testUpdateServicoNotFound() {
        when(servicoRepository.findById(999L)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            servicoService.update(999L, servico);
        });
        
        assertEquals("Servico not found with id: 999", exception.getMessage());
        verify(servicoRepository, times(1)).findById(999L);
        verify(servicoRepository, never()).save(any(Servico.class));
    }



    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por preço sem resultados")
    void testBuscarPorPrecoAteEmpty() {
        BigDecimal preco = new BigDecimal("1.00");
        when(servicoRepository.findByPrecoLessThanEqual(preco)).thenReturn(Arrays.asList());
        
        List<Servico> resultado = servicoService.buscarPorPrecoAte(preco);
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(servicoRepository, times(1)).findByPrecoLessThanEqual(preco);
    }
}