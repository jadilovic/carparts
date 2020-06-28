package com.avlija.parts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Post;

@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post, Integer> {
 
 Post findByUserId(Integer id);
 
 Post findByProductId(Long id);
 
 Post findByProductSifra(String sifra);

Page<Post> findByUserId(int userId, Pageable pageable);

Page<Post> findAll(Pageable pageable);
}