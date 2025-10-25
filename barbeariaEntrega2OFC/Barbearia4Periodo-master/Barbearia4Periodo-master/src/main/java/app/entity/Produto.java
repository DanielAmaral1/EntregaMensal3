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

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo descrição é obrigatório")
    private String descricao;

    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "O campo quantidade em estoque é obrigatório")
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

