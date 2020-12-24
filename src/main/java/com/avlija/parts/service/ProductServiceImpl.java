package com.avlija.parts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.repository.ProductRepository;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	  // AdminController lines 149 and 221
	// SearchController line 76
	@Override
	public List<Product> findProductsByGroup(ProductGroup productGroup) {
		return productRepository.findByProductGroup(productGroup);
	}

}