package com.avlija.parts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.ProductGroup;

@Repository("productGroupRepository")
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
 
 ProductGroup findByName(String name);
}