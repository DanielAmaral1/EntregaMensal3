package app.service;

import app.entity.Produto;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Produto produto) {
        // Regra de negócio: modificar objeto antes de persistir
        if (produto.getQuantidadeEstoque() == null || produto.getQuantidadeEstoque() <= 0) {
            produto.setQuantidadeEstoque(0);
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto update(Long id, Produto produtoDetails) {
        Produto produto = findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));

        produto.setNome(produtoDetails.getNome());
        produto.setDescricao(produtoDetails.getDescricao());
        produto.setPreco(produtoDetails.getPreco());
        produto.setQuantidadeEstoque(produtoDetails.getQuantidadeEstoque());

        return save(produto);
    }

    public List<Produto> buscarPorPrecoAte(BigDecimal preco) {
        return produtoRepository.findByPrecoLessThanEqual(preco);
    }

    public void deleteById(Long id) {
        // Regra de negócio complexa com exception
        Optional<Produto> produto = findById(id);
        if (produto.isEmpty()) {
            throw new RuntimeException("Não é possível excluir produto inexistente");
        }
        if (produto.get().getQuantidadeEstoque() > 0) {
            throw new RuntimeException("Não é possível excluir produto com estoque disponível");
        }
        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }

    public List<Produto> buscarProdutosComEstoqueBaixo(Integer quantidade) {
        return produtoRepository.buscarProdutosComEstoqueBaixo(quantidade);
    }
}