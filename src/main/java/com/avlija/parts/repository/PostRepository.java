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
	
	// MarketController line 186
	Post findByIdAndCountryAndActive(int postId, String country, int active);

	// MarketController line 139
// List<Post> findByProductSifra(String sifra);
 List<Post> findByProductSifraAndCountryAndActive(String sifra, String country, int active);

 // MarketController lines 209 and 218
 // List<Post> findByGroupId(Long id);
 List<Post> findByGroupIdAndCountryAndActive(Long groupId, String country, int active);


 // MarketController line 262
 Page<Post> findByUserId(int userId, Pageable pageable);

 // MarketController lines 355 and 362
 Page<Post> findAll(Pageable pageable);
 Page<Post> findByCountryAndActive(String country, int active, Pageable pageable);


// MarketController line 146
// Page<Post> findByProductSifra(String sifra, Pageable pageable);
Page<Post> findByProductSifraAndCountryAndActive(String productSifra, String country, int active, Pageable pageable);


// MarketController line 87
//List<Post> findByProductNameContaining(String productKeyword);

// MarketController line 98
// Page<Post> findByProductNameContaining(String productKeyword, Pageable pageable);

// MarketController line 90
List<Post> findByProductNameContainingAndCountryAndActive(String productKeyword, String country, int active);

// MarketController line 102
Page<Post> findByProductNameContainingAndCountryAndActive(String productKeyword, String country, int active, Pageable pageable);


}