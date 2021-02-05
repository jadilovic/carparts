package com.avlija.parts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Post;

@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post, Integer> {
/*
 * Market Posts Repository saving info about users posts in the market section
 */
// MarketController line 84
 List<Post> findByProductSifra(String sifra);

 // MarketController line 153
 List<Post> findByGroupId(Long id);

 // MarketController line 262
 Page<Post> findByUserId(int userId, Pageable pageable);

 // MarketController 294
 Page<Post> findAll(Pageable pageable);

// MarketController line 91
Page<Post> findByProductSifra(String sifra, Pageable pageable);

}