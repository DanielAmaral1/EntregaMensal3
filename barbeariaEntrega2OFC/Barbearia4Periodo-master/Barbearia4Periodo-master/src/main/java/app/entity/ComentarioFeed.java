package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comentario_feed")
public class ComentarioFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_comentario;

    @NotBlank(message = "O comentário é obrigatório")
    @Column(columnDefinition = "TEXT")
    private String texto;

    @Column(name = "data_comentario")
    private LocalDateTime dataComentario;

    @ManyToOne
    @JoinColumn(name = "id_post", nullable = false)
    @NotNull(message = "O post é obrigatório")
    @JsonIgnoreProperties("comentarios")
    private PostFeed post;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "O autor é obrigatório")
    @JsonIgnoreProperties({"posts", "avaliacoes", "agendamentos", "comentarios"})
    private Cliente autor;

    @PrePersist
    protected void onCreate() {
        dataComentario = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComentarioFeed that = (ComentarioFeed) o;
        return java.util.Objects.equals(id_comentario, that.id_comentario);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_comentario);
    }
}

