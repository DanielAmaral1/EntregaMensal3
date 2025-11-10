package app.controller;

import app.entity.Produto;
import app.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId_produto(1L);
        produto.setNome("Shampoo");
        produto.setDescricao("Shampoo anticaspa");
        produto.setPreco(new BigDecimal("25.90"));
        produto.setQuantidadeEstoque(10);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao salvar produto")
    void testSave() throws Exception {
        when(produtoService.save(any(Produto.class))).thenReturn(produto);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao listar todos os produtos")
    void testFindAll() throws Exception {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.findAll()).thenReturn(produtos);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por ID com sucesso")
    void testFindById() throws Exception {
        when(produtoService.findById(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de produto não encontrado por ID")
    void testFindByIdNotFound() throws Exception {
        when(produtoService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao atualizar produto")
    void testUpdate() throws Exception {
        when(produtoService.update(anyLong(), any(Produto.class))).thenReturn(produto);

        mockMvc.perform(put("/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao deletar produto")
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por nome com resultados")
    void testBuscarPorNome() throws Exception {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.buscarPorNome("Shampoo")).thenReturn(produtos);

        mockMvc.perform(get("/produtos/buscar")
                .param("nome", "Shampoo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por estoque baixo")
    void testBuscarEstoqueBaixo() throws Exception {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.buscarProdutosComEstoqueBaixo(5)).thenReturn(produtos);

        mockMvc.perform(get("/produtos/estoque-baixo")
                .param("quantidade", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Shampoo"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de busca por preço máximo")
    void testBuscarPorPrecoAte() throws Exception {
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoService.buscarPorPrecoAte(new BigDecimal("30.00"))).thenReturn(produtos);

        mockMvc.perform(get("/produtos/preco-ate")
                .param("preco", "30.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Shampoo"));
    }
}