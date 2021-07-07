package com.treykapfer.ustinblog.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.treykapfer.ustinblog.models.Post;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAll();

    @Query(value="SELECT * FROM posts ORDER BY created_at DESC;", nativeQuery=true)
    List<Post> findAllReverse();

}