package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_avaliacao;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser entre 1 e 5")
    @Max(value = 5, message = "A nota deve ser entre 1 e 5")
    private Integer nota;

    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "O cliente é obrigatório")
    @JsonIgnoreProperties({"avaliacoes", "agendamentos"})
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_agendamento", nullable = false)
    @NotNull(message = "O agendamento é obrigatório")
    @JsonIgnoreProperties({"avaliacoes", "cliente", "funcionario", "servico", "produtos"})
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = true)
    @JsonIgnoreProperties({"avaliacoes", "agendamentos"})
    private Funcionario funcionario;

    @PrePersist
    protected void onCreate() {
        dataAvaliacao = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avaliacao avaliacao = (Avaliacao) o;
        return java.util.Objects.equals(id_avaliacao, avaliacao.id_avaliacao);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_avaliacao);
    }
}

