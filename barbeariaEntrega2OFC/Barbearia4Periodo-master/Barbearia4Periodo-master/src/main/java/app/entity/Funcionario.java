package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_funcionario;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @NotBlank(message = "O campo telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "O campo endereço é obrigatório")
    private String endereco;

    // Relacionamentos removidos temporariamente
    // @OneToMany(mappedBy = "funcionario")
    // private List<Agendamento> agendamentos;

    // @ManyToMany
    // private List<Servico> servicos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return java.util.Objects.equals(id_funcionario, that.id_funcionario);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_funcionario);
    }
}

