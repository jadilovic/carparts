package com.avlija.parts.repository;


import org.springframework.data.repository.CrudRepository;

import com.avlija.parts.model.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long> {
    
    Brand findByName(String name);
    
    
}
