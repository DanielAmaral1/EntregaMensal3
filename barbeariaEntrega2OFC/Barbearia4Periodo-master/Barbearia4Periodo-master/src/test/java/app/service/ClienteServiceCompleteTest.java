package app.service;

import app.entity.Cliente;
import app.repository.ClienteRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceCompleteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId_cliente(1L);
        cliente.setNome("Maria Silva");
        cliente.setEmail("maria@email.com");
        cliente.setCelular("11-987654321");
        cliente.setIdade(25);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar todos os clientes")
    void testFindAllSuccess() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
        
        List<Cliente> resultado = clienteService.findAll();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar cliente por ID")
    void testFindByIdSuccess() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        
        Optional<Cliente> resultado = clienteService.findById(1L);
        
        assertTrue(resultado.isPresent());
        assertEquals("Maria Silva", resultado.get().getNome());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por nome")
    void testBuscarPorNomeSuccess() {
        when(clienteRepository.findByNomeContaining("Maria")).thenReturn(Arrays.asList(cliente));
        
        List<Cliente> resultado = clienteService.buscarPorNome("Maria");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByNomeContaining("Maria");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao buscar por idade")
    void testBuscarPorIdadeSuccess() {
        when(clienteRepository.findByIdadeGreaterThanEqual(25)).thenReturn(Arrays.asList(cliente));
        
        List<Cliente> resultado = clienteService.buscarPorIdade(25);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByIdadeGreaterThanEqual(25);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de pesquisa global com termo vazio")
    void testPesquisarGlobalTermoVazio() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));
        
        List<Cliente> resultado = clienteService.pesquisarGlobal("");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de pesquisa global com termo numérico")
    void testPesquisarGlobalTermoNumerico() {
        when(clienteRepository.findByNomeContaining("25")).thenReturn(Arrays.asList());
        when(clienteRepository.findByEmailContainingIgnoreCase("25")).thenReturn(Arrays.asList());
        when(clienteRepository.findByCelularContaining("25")).thenReturn(Arrays.asList());
        when(clienteRepository.findByIdadeGreaterThanEqual(25)).thenReturn(Arrays.asList(cliente));
        
        List<Cliente> resultado = clienteService.pesquisarGlobal("25");
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository, times(1)).findByIdadeGreaterThanEqual(25);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de sucesso ao atualizar cliente")
    void testUpdateClienteSuccess() {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Maria Santos");
        clienteAtualizado.setEmail("maria.santos@email.com");
        clienteAtualizado.setCelular("11-999888777");
        clienteAtualizado.setIdade(26);
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);
        
        Cliente resultado = clienteService.update(1L, clienteAtualizado);
        
        assertNotNull(resultado);
        assertEquals("Maria Santos", resultado.getNome());
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}