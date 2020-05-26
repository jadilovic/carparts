package com.avlija.parts.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.avlija.parts.model.Brand;
import com.avlija.parts.model.CarModel;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {
    
    CarModel findByName(String name);
    
    List<CarModel> findByBrand(Brand brand);
}
