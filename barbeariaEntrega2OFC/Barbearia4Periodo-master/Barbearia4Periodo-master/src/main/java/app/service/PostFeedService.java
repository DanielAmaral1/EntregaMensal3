package app.service;

import app.entity.PostFeed;
import app.entity.ComentarioFeed;
import app.repository.PostFeedRepository;
import app.repository.ComentarioFeedRepository;
import app.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostFeedService {

    private final PostFeedRepository postFeedRepository;
    private final ComentarioFeedRepository comentarioFeedRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public PostFeedService(
            PostFeedRepository postFeedRepository,
            ComentarioFeedRepository comentarioFeedRepository,
            ClienteRepository clienteRepository) {
        this.postFeedRepository = postFeedRepository;
        this.comentarioFeedRepository = comentarioFeedRepository;
        this.clienteRepository = clienteRepository;
    }

    public PostFeed save(PostFeed post) {
        if (post.getAutor() != null && post.getAutor().getId_cliente() != null) {
            if (post.getAutor().getId_cliente() > 0) {
                post.setAutor(clienteRepository.findById(post.getAutor().getId_cliente())
                        .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
            } else {
                // Para Master (id_cliente = 0), usar autorNome e autor = null
                post.setAutorNome("Route 48");
                post.setAutor(null);
            }
        } else {
            throw new IllegalArgumentException("Autor é obrigatório");
        }
        return postFeedRepository.save(post);
    }

    public List<PostFeed> findAll() {
        List<PostFeed> posts = postFeedRepository.findAll();
        posts.sort((a, b) -> {
            if (a.getDataPost() == null && b.getDataPost() == null) return 0;
            if (a.getDataPost() == null) return 1;
            if (b.getDataPost() == null) return -1;
            return b.getDataPost().compareTo(a.getDataPost());
        });
        return posts;
    }

    public Optional<PostFeed> findById(Long id) {
        return postFeedRepository.findById(id);
    }

    public PostFeed update(Long id, PostFeed postDetails) {
        PostFeed post = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com id: " + id));

        post.setConteudo(postDetails.getConteudo());
        return postFeedRepository.save(post);
    }

    public void deleteById(Long id) {
        postFeedRepository.deleteById(id);
    }



    public List<PostFeed> findByAutor(Long idCliente) {
        return postFeedRepository.findByAutorId(idCliente);
    }

    public List<PostFeed> pesquisarPorConteudo(String termo) {
        return postFeedRepository.findByConteudoContainingIgnoreCase(termo);
    }


}

