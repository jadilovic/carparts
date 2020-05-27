package com.avlija.parts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;

@Repository("productRepository")
public interface ProductRepository extends CrudRepository<Product, Long> {
 
 Product findBySifra(String sifra);
 
 List<Product> findByProductGroup(ProductGroup productGroup);
 
 List<Product> findByDescriptionLikeAndProductGroup(String pattern, ProductGroup productGroup);
}