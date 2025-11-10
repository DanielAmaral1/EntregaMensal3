package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "servico")
@Getter
@Setter
public class ServicoSimple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_servico;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer duracaoMinutos;

    public ServicoSimple() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicoSimple servico = (ServicoSimple) o;
        return java.util.Objects.equals(id_servico, servico.id_servico);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_servico);
    }
}