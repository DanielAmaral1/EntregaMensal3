package app.service;

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
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
import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceCompleteTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setId_funcionario(1L);
        funcionario.setNome("Carlos Silva");
        funcionario.setTelefone("11-987654321");
        funcionario.setEndereco("Rua A, 123");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar todos os funcionários")
    void testFindAllSuccess() {
        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario));
        
        List<Funcionario> resultado = funcionarioService.findAll();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(funcionarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar funcionário por ID")
    void testFindByIdSuccess() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        
        Funcionario resultado = funcionarioService.findById(1L);
        
        assertNotNull(resultado);
        assertEquals("Carlos Silva", resultado.getNome());
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de erro ao buscar funcionário por ID inexistente")
    void testFindByIdNotFound() {
        when(funcionarioRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.findById(999L);
        });
        verify(funcionarioRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por nome")
    void testBuscarPorNomeSuccess() {
        when(funcionarioRepository.findByNomeContainingIgnoreCase("Carlos")).thenReturn(Arrays.asList(funcionario));
        
        List<Funcionario> resultado = funcionarioService.buscarPorNome("Carlos");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(funcionarioRepository, times(1)).findByNomeContainingIgnoreCase("Carlos");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por telefone")
    void testBuscarPorTelefoneSuccess() {
        when(funcionarioRepository.findByTelefoneContaining("987")).thenReturn(Arrays.asList(funcionario));
        
        List<Funcionario> resultado = funcionarioService.buscarPorTelefone("987");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(funcionarioRepository, times(1)).findByTelefoneContaining("987");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao atualizar funcionário")
    void testUpdateFuncionarioSuccess() {
        Funcionario funcionarioAtualizado = new Funcionario();
        funcionarioAtualizado.setNome("Carlos Santos");
        funcionarioAtualizado.setTelefone("11-999888777");
        funcionarioAtualizado.setEndereco("Rua B, 456");
        
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioAtualizado);
        
        Funcionario resultado = funcionarioService.update(1L, funcionarioAtualizado);
        
        assertNotNull(resultado);
        assertEquals("Carlos Santos", resultado.getNome());
        verify(funcionarioRepository, times(1)).findById(1L);
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao deletar funcionário")
    void testDeleteFuncionarioSuccess() {
        when(funcionarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(funcionarioRepository).deleteById(1L);
        
        funcionarioService.delete(1L);
        
        verify(funcionarioRepository, times(1)).existsById(1L);
        verify(funcionarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar funcionário")
    void testSaveFuncionarioSuccess() {
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);
        
        Funcionario resultado = funcionarioService.save(funcionario);
        
        assertNotNull(resultado);
        assertEquals("Carlos Silva", resultado.getNome());
        verify(funcionarioRepository, times(1)).save(funcionario);
    }
}