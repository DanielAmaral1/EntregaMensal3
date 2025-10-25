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
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_servico;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo descrição é obrigatório")
    private String descricao;

    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal preco;

    private Integer duracaoMinutos;

    // Relacionamentos removidos temporariamente para debug
    // @ManyToMany(mappedBy = "servicos")
    // private List<Funcionario> funcionarios;

    // @OneToMany(mappedBy = "servico")
    // private List<Agendamento> agendamentos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return java.util.Objects.equals(id_servico, servico.id_servico);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_servico);
    }
}
