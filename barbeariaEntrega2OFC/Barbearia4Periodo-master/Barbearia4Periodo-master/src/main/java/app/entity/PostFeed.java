package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post_feed")
public class PostFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_post;

    @NotBlank(message = "O conteúdo é obrigatório")
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @Column(name = "data_post")
    private LocalDateTime dataPost;

    @Column(name = "curtidas")
    private Integer curtidas = 0;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "O autor é obrigatório")
    @JsonIgnoreProperties({"posts", "avaliacoes", "agendamentos"})
    private Cliente autor;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("post")
    private List<ComentarioFeed> comentarios = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataPost = LocalDateTime.now();
        if (curtidas == null) {
            curtidas = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostFeed postFeed = (PostFeed) o;
        return java.util.Objects.equals(id_post, postFeed.id_post);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id_post);
    }
}

