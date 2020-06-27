package com.avlija.parts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.BrandRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.repository.TransactionRepository;
import com.avlija.parts.repository.UserRepository;
import com.avlija.parts.service.ProductServiceImpl;
import com.avlija.parts.service.UserService;

@Controller
public class MarketController {
	
	 @Autowired
	 private ProductRepository productRepository;
	 
	 @Autowired
	 private ProductServiceImpl productServiceImpl;
	 
	 @Autowired
	 private BrandRepository brandRepository;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private TransactionRepository transactionRepository;
	 
	 @Autowired
	 private ProductQuantityRepository productQuantityRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
 
	 @RequestMapping(value= {"/user/market/{id}"}, method=RequestMethod.GET)
	 public ModelAndView productProfile(@PathVariable(name = "id") Long id) {
	  ModelAndView model = new ModelAndView();
	  Product product = productRepository.findById(id).get();
	  User user = getCurrentUser();
	  List<Product> replaceProducts = product.getProducts();
	  ProductQuantity productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
	  		
	  if(productQuantity.getQuantity() <= 0) {
		  model.addObject("product", product);
		  model.addObject("productQuantity", productQuantity);
		  model.addObject("replaceProducts", replaceProducts);
			model.addObject("msg", "Nije moguce objaviti u oglasniku jer nemate proizvod na stanju");
  			model.setViewName("home/product_profile");
	  } else {
	  			model.addObject("productQuantity", productQuantity);
	  			model.addObject("msg", "Transakcija proizvoda na stanju!");
	  			model.setViewName("admin/product_add_remove");
	  			model.addObject("product", product);
	  		}
	  return model;
	 }
	 
	 
	 private User getCurrentUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			return user;
	}
	/*
 @RequestMapping(value= {"/clientservices"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/clientServices");
  return model;
 }
 
 @RequestMapping(value= {"/becomeclient"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/becomeClient");
  
  return model;
 }
 */
}
