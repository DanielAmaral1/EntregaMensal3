package app.repository;

import app.entity.ComentarioFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioFeedRepository extends JpaRepository<ComentarioFeed, Long> {
    
    List<ComentarioFeed> findByPostIdPost(Long idPost);
    
    List<ComentarioFeed> findByAutorIdCliente(Long idCliente);
    
    List<ComentarioFeed> findByOrderByDataComentarioDesc();
}

