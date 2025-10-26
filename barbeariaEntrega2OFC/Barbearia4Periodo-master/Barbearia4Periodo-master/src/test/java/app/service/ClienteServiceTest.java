package app.service;

import app.entity.Cliente;
import app.repository.ClienteRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setCelular("11-987654321");
        cliente.setIdade(30);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao salvar cliente")
    void testSaveClienteSuccess() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente resultado = clienteService.save(cliente);
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário com dados incorretos e que lança exceção")
    void testUpdateClienteNotFound() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            clienteService.update(999L, cliente);
        });
        verify(clienteRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de pesquisa global com termo válido")
    void testPesquisarGlobalSuccess() {
        String termo = "João";
        when(clienteRepository.findByNomeContaining(termo)).thenReturn(Arrays.asList(cliente));
        when(clienteRepository.findByEmailContainingIgnoreCase(termo)).thenReturn(Arrays.asList());
        when(clienteRepository.findByCelularContaining(termo)).thenReturn(Arrays.asList());
        
        List<Cliente> resultado = clienteService.pesquisarGlobal(termo);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByNomeContaining(termo);
    }
}