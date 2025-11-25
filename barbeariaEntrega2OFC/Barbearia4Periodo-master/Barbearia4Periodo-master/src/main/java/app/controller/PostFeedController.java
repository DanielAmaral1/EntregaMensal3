package app.controller;

import app.entity.PostFeed;
import app.entity.ComentarioFeed;
import app.service.PostFeedService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feed")
public class PostFeedController {

    @Autowired
    private PostFeedService postFeedService;

    @PostMapping
    public ResponseEntity<PostFeed> createPost(@RequestBody PostFeed post) {
        PostFeed savedPost = postFeedService.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostFeed>> getAllPosts() {
        return ResponseEntity.ok(postFeedService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostFeed> findById(@PathVariable Long id) {
        return postFeedService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Post não encontrado com id: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostFeed> updatePost(
            @PathVariable Long id,
            @RequestBody PostFeed postDetails) {
        return ResponseEntity.ok(postFeedService.update(id, postDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postFeedService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/autor/{idCliente}")
    public ResponseEntity<List<PostFeed>> findByAutor(@PathVariable Long idCliente) {
        return ResponseEntity.ok(postFeedService.findByAutor(idCliente));
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<PostFeed>> pesquisarPorConteudo(@RequestParam String termo) {
        return ResponseEntity.ok(postFeedService.pesquisarPorConteudo(termo));
    }


}

