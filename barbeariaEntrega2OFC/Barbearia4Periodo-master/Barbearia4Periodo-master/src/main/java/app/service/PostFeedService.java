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
            post.setAutor(clienteRepository.findById(post.getAutor().getId_cliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
        }
        return postFeedRepository.save(post);
    }

    public List<PostFeed> findAll() {
        return postFeedRepository.findByOrderByDataPostDesc();
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

    @Transactional
    public PostFeed curtirPost(Long idPost) {
        PostFeed post = findById(idPost)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com id: " + idPost));
        post.setCurtidas(post.getCurtidas() + 1);
        return postFeedRepository.save(post);
    }

    @Transactional
    public PostFeed descurtirPost(Long idPost) {
        PostFeed post = findById(idPost)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com id: " + idPost));
        if (post.getCurtidas() > 0) {
            post.setCurtidas(post.getCurtidas() - 1);
        }
        return postFeedRepository.save(post);
    }

    public ComentarioFeed adicionarComentario(Long idPost, ComentarioFeed comentario) {
        PostFeed post = findById(idPost)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com id: " + idPost));

        if (comentario.getAutor() != null && comentario.getAutor().getId_cliente() != null) {
            comentario.setAutor(clienteRepository.findById(comentario.getAutor().getId_cliente())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")));
        }

        comentario.setPost(post);
        return comentarioFeedRepository.save(comentario);
    }

    public void removerComentario(Long idComentario) {
        comentarioFeedRepository.deleteById(idComentario);
    }

    public List<ComentarioFeed> getComentariosByPost(Long idPost) {
        return comentarioFeedRepository.findByPostIdPost(idPost);
    }

    public List<PostFeed> findByAutor(Long idCliente) {
        return postFeedRepository.findByAutorIdCliente(idCliente);
    }

    public List<PostFeed> pesquisarPorConteudo(String termo) {
        return postFeedRepository.findByConteudoContainingIgnoreCase(termo);
    }

    public List<PostFeed> findByCurtidasMinimas(Integer curtidas) {
        return postFeedRepository.findByCurtidasGreaterThanEqual(curtidas);
    }
}

