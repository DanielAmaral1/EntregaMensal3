package app.repository;

import app.entity.PostFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFeedRepository extends JpaRepository<PostFeed, Long> {
    
    List<PostFeed> findByAutorIdCliente(Long idCliente);
    
    List<PostFeed> findByOrderByDataPostDesc();
    
    List<PostFeed> findByConteudoContainingIgnoreCase(String termo);
    
    List<PostFeed> findByCurtidasGreaterThanEqual(Integer curtidas);
}

