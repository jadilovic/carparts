package com.avlija.parts.service;

import java.util.List;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;

public interface ProductService {
  
public List<Product> findProductsByGroup(ProductGroup productGroup);

}