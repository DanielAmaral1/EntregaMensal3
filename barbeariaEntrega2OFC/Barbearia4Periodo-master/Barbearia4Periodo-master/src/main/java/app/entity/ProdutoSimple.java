package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class ProdutoSimple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    public ProdutoSimple() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoSimple produto = (ProdutoSimple) o;
        return java.util.Objects.equals(id_produto, produto.id_produto);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_produto);
    }
}