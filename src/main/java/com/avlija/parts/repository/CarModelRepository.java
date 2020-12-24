package com.avlija.parts.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.avlija.parts.model.Brand;
import com.avlija.parts.model.CarModel;

public interface CarModelRepository extends CrudRepository<CarModel, Long> {
    /*
     * Car Model Repository
     */
	// Used in SearchController line 217
	// Used in AdminController line 428
    CarModel findByName(String name);
    
    // Used in SearchController line 200
    List<CarModel> findByBrand(Brand brand);
}
