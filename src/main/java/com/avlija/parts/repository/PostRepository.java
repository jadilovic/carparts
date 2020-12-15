package com.avlija.parts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Post;

@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post, Integer> {
 
 //Post findByUserId(Integer id);
 
 // Post findByProductId(Long id);
 
 List<Post> findByProductSifra(String sifra);
 
 List<Post> findByProductId(Long id);
 
 List<Post> findByGroupId(Long id);

Page<Post> findByUserId(int userId, Pageable pageable);

Page<Post> findAll(Pageable pageable);

Page<Post> findByProductSifra(String sifra, Pageable pageable);

Page<Post> findByProductId(Long id, Pageable pageable);

Page<Post> findByGroupId(Long id, Pageable pageable);
}