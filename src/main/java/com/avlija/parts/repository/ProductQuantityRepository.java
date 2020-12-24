
package com.avlija.parts.repository;

import org.springframework.stereotype.Repository;

import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.UserProduct;

import org.springframework.data.jpa.repository.JpaRepository;

// Used in UserController, SearchController, MarketController and AdminController

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, UserProduct>{

}

