package app.repository;

import app.entity.PostFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFeedRepository extends JpaRepository<PostFeed, Long> {
    
    @Query("SELECT p FROM PostFeed p WHERE p.autor.id_cliente = :idCliente")
    List<PostFeed> findByAutorId(@Param("idCliente") Long idCliente);
    
    List<PostFeed> findByOrderByDataPostDesc();
    
    List<PostFeed> findByConteudoContainingIgnoreCase(String termo);
    
    List<PostFeed> findByCurtidasGreaterThanEqual(Integer curtidas);
    

}

