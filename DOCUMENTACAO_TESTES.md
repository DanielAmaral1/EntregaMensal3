# Documentacao de Testes - Sistema Barbearia

## Tipos de teste

### 1. Testes Unitarios
Testam uma parte pequena do codigo por vez.

Exemplo do projeto:
```java
@Test
@DisplayName("TESTE DE UNIDADE - Cliente nao encontrado")
void testUpdateClienteNotFound() {
    // Simula que nao achou o cliente
    when(clienteRepository.findById(999L)).thenReturn(Optional.empty());
    
    // Verifica se da erro quando tenta atualizar
    assertThrows(EntityNotFoundException.class, () -> {
        clienteService.update(999L, cliente);
    });
}
```

### 2. Testes de Integracao
Testam se as partes do sistema funcionam juntas.

Exemplo do projeto:
```java
@Test
@DisplayName("TESTE DE INTEGRACAO - Buscar cliente por ID")
void testFindClienteById() throws Exception {
    // Prepara o teste
    when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));
    
    // Faz a requisicao HTTP
    mockMvc.perform(get("/clientes/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Joao Silva"));
}
```

## Cenarios

### Casos que funcionam:
- Criar cliente
- Buscar cliente
- Atualizar cliente  
- Deletar cliente
- Listar todos os clientes

### Casos de erro:
- Cliente nao existe
- Dados invalidos
- Lista vazia

### Casos especiais:
- Busca sem resultado
- Parametros vazios

## Como executar os testes

```bash
mvn test
```