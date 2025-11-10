package app.service;

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos Barbeiro");
        funcionario.setTelefone("11-999888777");
        funcionario.setEndereco("Rua das Flores, 123");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar funcionário")
    void testSaveFuncionarioSuccess() {
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);
        
        Funcionario resultado = funcionarioService.save(funcionario);
        
        assertNotNull(resultado);
        assertEquals("Carlos Barbeiro", resultado.getNome());
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao deletar funcionário inexistente")
    void testDeleteFuncionarioNotFound() {
        when(funcionarioRepository.existsById(999L)).thenReturn(false);
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.delete(999L);
        });
        
        assertEquals("Funcionario not found with id: 999", exception.getMessage());
        verify(funcionarioRepository, never()).deleteById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por nome com resultados")
    void testBuscarPorNome() {
        List<Funcionario> funcionarios = Arrays.asList(funcionario);
        when(funcionarioRepository.findByNomeContainingIgnoreCase("Carlos")).thenReturn(funcionarios);
        
        List<Funcionario> resultado = funcionarioService.buscarPorNome("Carlos");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Carlos Barbeiro", resultado.get(0).getNome());
        verify(funcionarioRepository, times(1)).findByNomeContainingIgnoreCase("Carlos");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar funcionario por ID inexistente")
    void testFindByIdNotFound() {
        when(funcionarioRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.findById(999L);
        });
        
        assertEquals("Funcionario not found with id: 999", exception.getMessage());
        verify(funcionarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de busca por telefone sem resultados")
    void testBuscarPorTelefoneEmpty() {
        when(funcionarioRepository.findByTelefoneContaining("999999999")).thenReturn(Arrays.asList());
        
        List<Funcionario> resultado = funcionarioService.buscarPorTelefone("999999999");
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(funcionarioRepository, times(1)).findByTelefoneContaining("999999999");
    }
}