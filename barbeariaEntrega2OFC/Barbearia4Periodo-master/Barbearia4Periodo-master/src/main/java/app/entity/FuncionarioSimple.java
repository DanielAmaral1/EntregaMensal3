package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
public class FuncionarioSimple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_funcionario;

    private String nome;
    private String telefone;
    private String endereco;

    public FuncionarioSimple() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuncionarioSimple that = (FuncionarioSimple) o;
        return java.util.Objects.equals(id_funcionario, that.id_funcionario);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_funcionario);
    }
}