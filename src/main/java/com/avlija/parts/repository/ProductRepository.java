package com.avlija.parts.repository;

import java.util.List;

// Used in MarketController, AdminController, SearchController and UserController
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;

@Repository("productRepository")
public interface ProductRepository extends CrudRepository<Product, Long> {
 
	// SearchController line 145
	// AdminController line 149
 Product findBySifra(String sifra);
 
 // SearchController line 230
 List<Product> findByProductGroup(ProductGroup productGroup);
 
 // SearchController line 230
 List<Product> findByDescriptionLikeAndProductGroup(String pattern, ProductGroup productGroup);

List<Product> findByNameContaining(String keyWord);
}