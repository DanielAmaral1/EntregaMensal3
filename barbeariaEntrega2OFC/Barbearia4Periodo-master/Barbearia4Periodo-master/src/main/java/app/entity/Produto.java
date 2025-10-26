package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;

    // Relacionamentos removidos temporariamente
    // @ManyToMany(mappedBy = "produtos")
    // private List<Agendamento> agendamentos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return java.util.Objects.equals(id_produto, produto.id_produto);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_produto);
    }
}

