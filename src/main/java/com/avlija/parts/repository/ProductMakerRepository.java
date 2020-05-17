package com.avlija.parts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.ProductMaker;

@Repository("productMakerRepository")
public interface ProductMakerRepository extends JpaRepository<ProductMaker, Long> {
 
 ProductMaker findByName(String name);
}