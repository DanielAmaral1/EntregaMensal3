package app.service;

import app.entity.Servico;
import app.repository.ServicoRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicoServiceCompleteTest {

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoService servicoService;

    private Servico servico;

    @BeforeEach
    void setUp() {
        servico = new Servico();
        servico.setId_servico(1L);
        servico.setNome("Barba");
        servico.setDescricao("Aparar barba");
        servico.setPreco(new BigDecimal("15.00"));
        servico.setDuracaoMinutos(20);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar todos os serviços")
    void testFindAllSuccess() {
        when(servicoRepository.findAll()).thenReturn(Arrays.asList(servico));
        
        List<Servico> resultado = servicoService.findAll();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(servicoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar serviço por ID")
    void testFindByIdSuccess() {
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        
        Servico resultado = servicoService.findById(1L);
        
        assertNotNull(resultado);
        assertEquals("Barba", resultado.getNome());
        verify(servicoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar serviço por ID inexistente")
    void testFindByIdNotFound() {
        when(servicoRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            servicoService.findById(999L);
        });
        verify(servicoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por nome")
    void testBuscarPorNomeSuccess() {
        when(servicoRepository.findByNomeIgnoreCaseContaining("Barba")).thenReturn(Arrays.asList(servico));
        
        List<Servico> resultado = servicoService.buscarPorNome("Barba");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(servicoRepository, times(1)).findByNomeIgnoreCaseContaining("Barba");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por preço até valor")
    void testBuscarPorPrecoAteSuccess() {
        BigDecimal preco = new BigDecimal("20.00");
        when(servicoRepository.findByPrecoLessThanEqual(preco)).thenReturn(Arrays.asList(servico));
        
        List<Servico> resultado = servicoService.buscarPorPrecoAte(preco);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(servicoRepository, times(1)).findByPrecoLessThanEqual(preco);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar quantidade de serviços")
    void testBuscarQtdServicoSuccess() {
        when(servicoRepository.buscarQtdServico()).thenReturn(5L);
        
        Long resultado = servicoService.buscarQtdServico();
        
        assertNotNull(resultado);
        assertEquals(5L, resultado);
        verify(servicoRepository, times(1)).buscarQtdServico();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao atualizar serviço")
    void testUpdateServicoSuccess() {
        Servico servicoAtualizado = new Servico();
        servicoAtualizado.setNome("Barba Premium");
        servicoAtualizado.setDescricao("Aparar e modelar barba");
        servicoAtualizado.setPreco(new BigDecimal("25.00"));
        servicoAtualizado.setDuracaoMinutos(30);
        
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servicoAtualizado);
        
        Servico resultado = servicoService.update(1L, servicoAtualizado);
        
        assertNotNull(resultado);
        assertEquals("Barba Premium", resultado.getNome());
        verify(servicoRepository, times(1)).findById(1L);
        verify(servicoRepository, times(1)).save(any(Servico.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao deletar serviço")
    void testDeleteServicoSuccess() {
        when(servicoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(servicoRepository).deleteById(1L);
        
        servicoService.deleteById(1L);
        
        verify(servicoRepository, times(1)).existsById(1L);
        verify(servicoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar serviço")
    void testSaveServicoSuccess() {
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);
        
        Servico resultado = servicoService.save(servico);
        
        assertNotNull(resultado);
        assertEquals("Barba", resultado.getNome());
        verify(servicoRepository, times(1)).save(servico);
    }
}