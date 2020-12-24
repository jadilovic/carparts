package com.avlija.parts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.ProductMaker;

//Used in AdminController

@Repository("productMakerRepository")
public interface ProductMakerRepository extends JpaRepository<ProductMaker, Long> {
 
	// AdminController line 114
 ProductMaker findByName(String name);
}