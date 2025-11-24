package app.repository;

import app.entity.ComentarioFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioFeedRepository extends JpaRepository<ComentarioFeed, Long> {
    
    @Query("SELECT c FROM ComentarioFeed c WHERE c.post.id_post = :idPost")
    List<ComentarioFeed> findByPostId(@Param("idPost") Long idPost);
    
    @Query("SELECT c FROM ComentarioFeed c WHERE c.autor.id_cliente = :idCliente")
    List<ComentarioFeed> findByAutorId(@Param("idCliente") Long idCliente);
    
    List<ComentarioFeed> findByOrderByDataComentarioDesc();
    

}

