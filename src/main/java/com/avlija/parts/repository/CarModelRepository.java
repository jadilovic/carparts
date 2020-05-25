package com.avlija.parts.repository;


import org.springframework.data.repository.CrudRepository;

import com.avlija.parts.model.CarModel;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {
    
    CarModel findByName(String name);
    
    
}
