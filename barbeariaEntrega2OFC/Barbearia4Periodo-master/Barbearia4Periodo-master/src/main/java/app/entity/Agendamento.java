package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Agendamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_agendamento;

    @NotNull(message = "O campo data/hora é obrigatório")
    private LocalDateTime dataHora;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "O campo cliente é obrigatório")
    @JsonIgnoreProperties("agendamentos")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    @NotNull(message = "O campo funcionário é obrigatório")
    @JsonIgnoreProperties("agendamentos")
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_servico", nullable = false)
    @NotNull(message = "O campo serviço é obrigatório")
    @JsonIgnoreProperties("agendamentos")
    private Servico servico;

    @ManyToMany
    @JoinTable(
            name = "agendamento_produto",
            joinColumns = @JoinColumn(name = "id_agendamento"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    @JsonIgnoreProperties("agendamentos")
    private List<Produto> produtos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return java.util.Objects.equals(id_agendamento, that.id_agendamento);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_agendamento);
    }
}
