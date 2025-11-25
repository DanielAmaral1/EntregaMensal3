package app.repository;

import app.entity.PostFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFeedRepository extends JpaRepository<PostFeed, Long> {
    
    @Query("SELECT p FROM PostFeed p WHERE (p.autor IS NOT NULL AND p.autor.id_cliente = :idCliente) OR (p.autor IS NULL AND :idCliente = 0)")
    List<PostFeed> findByAutorId(@Param("idCliente") Long idCliente);
    

    
    List<PostFeed> findByConteudoContainingIgnoreCase(String termo);
    

    

}

