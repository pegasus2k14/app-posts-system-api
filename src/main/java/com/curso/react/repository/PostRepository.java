package com.curso.react.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.curso.react.entity.PostEntity;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long>{
       
    List<PostEntity> getByUserIdOrderByCreatedAtDesc(Long userId);

    @Query(nativeQuery = true, 
     value = "SELECT *  FROM posts p WHERE p.exposure_id =:exposureId and p.expires_at >:now ORDER BY created_at DESC LIMIT 20")
    List<PostEntity> getLastPublicPost(@Param("exposureId") Long exposureId, @Param("now") Date now);

    PostEntity  findByPostId(String postId);
}
