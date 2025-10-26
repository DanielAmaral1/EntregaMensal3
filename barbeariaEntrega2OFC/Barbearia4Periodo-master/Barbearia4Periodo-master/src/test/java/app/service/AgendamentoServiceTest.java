package app.service;

import app.entity.Agendamento;
import app.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private Agendamento agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new Agendamento();
        agendamento.setId_agendamento(1L);
        agendamento.setDataHora(LocalDateTime.now());
        agendamento.setObservacoes("Teste");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar agendamento")
    void testSaveAgendamentoSuccess() {
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        
        Agendamento resultado = agendamentoService.save(agendamento);
        
        assertNotNull(resultado);
        assertEquals("Teste", resultado.getObservacoes());
        verify(agendamentoRepository, times(1)).save(agendamento);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar agendamento por ID inexistente")
    void testFindByIdNotFound() {
        when(agendamentoRepository.findById(999L)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            agendamentoService.findById(999L);
        });
        
        assertEquals("Agendamento not found with id: 999", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao deletar agendamento inexistente")
    void testDeleteAgendamentoNotFound() {
        when(agendamentoRepository.existsById(999L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            agendamentoService.deleteById(999L);
        });
        
        assertEquals("Agendamento not found with id: 999", exception.getMessage());
        verify(agendamentoRepository, never()).deleteById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao listar todos os agendamentos")
    void testFindAllSuccess() {
        List<Agendamento> agendamentos = Arrays.asList(agendamento);
        when(agendamentoRepository.findAll()).thenReturn(agendamentos);
        
        List<Agendamento> resultado = agendamentoService.findAll();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(agendamentoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao atualizar agendamento inexistente")
    void testUpdateAgendamentoNotFound() {
        when(agendamentoRepository.findById(999L)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            agendamentoService.update(999L, agendamento);
        });
        
        assertEquals("Agendamento not found with id: 999", exception.getMessage());
        verify(agendamentoRepository, times(1)).findById(999L);
        verify(agendamentoRepository, never()).save(any(Agendamento.class));
    }
}