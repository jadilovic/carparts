package com.avlija.parts.service;

import java.util.List;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;

public interface ProductService {
	
  // AdminController lines 149 and 221
	// SearchController line 76
public List<Product> findProductsByGroup(ProductGroup productGroup);

}