package app.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServicoTest {

    private Servico servico;

    @BeforeEach
    void setUp() {
        servico = new Servico();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do ID")
    void testIdServicoGetterSetter() {
        Long id = 1L;
        servico.setId_servico(id);
        assertEquals(id, servico.getId_servico());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do nome")
    void testNomeGetterSetter() {
        String nome = "Corte Masculino";
        servico.setNome(nome);
        assertEquals(nome, servico.getNome());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da descrição")
    void testDescricaoGetterSetter() {
        String descricao = "Corte tradicional";
        servico.setDescricao(descricao);
        assertEquals(descricao, servico.getDescricao());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters do preço")
    void testPrecoGetterSetter() {
        BigDecimal preco = new BigDecimal("25.00");
        servico.setPreco(preco);
        assertEquals(preco, servico.getPreco());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de teste dos getters e setters da duração")
    void testDuracaoMinutosGetterSetter() {
        Integer duracao = 30;
        servico.setDuracaoMinutos(duracao);
        assertEquals(duracao, servico.getDuracaoMinutos());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE - Cenário de criação de serviço com construtor padrão")
    void testConstrutorPadrao() {
        Servico novoServico = new Servico();
        assertNotNull(novoServico);
        assertNull(novoServico.getId_servico());
        assertNull(novoServico.getNome());
        assertNull(novoServico.getDescricao());
        assertNull(novoServico.getPreco());
        assertNull(novoServico.getDuracaoMinutos());
    }
}