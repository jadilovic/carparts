package com.avlija.parts.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.model.ProductMaker;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductMakerRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.service.ProductServiceImpl;
import com.avlija.parts.service.UserService;

@Controller
public class AdminController {

 @Autowired
 private UserService userService;
 
 @Autowired
 private ProductGroupRepository productGroupRepository;
 
 @Autowired
 private ProductMakerRepository productMakerRepository;
 
 @Autowired
 private ProductRepository productRepository;
 
 @Autowired
 private ProductServiceImpl productServiceImpl;


 @RequestMapping(value= {"admin/creategroup"}, method=RequestMethod.GET)
 public ModelAndView createGroup() {
  ModelAndView model = new ModelAndView();
  ProductGroup productGroup = new ProductGroup();
  model.addObject("productGroup", productGroup);
  model.setViewName("admin/create_group");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/creategroup"}, method=RequestMethod.POST)
 public ModelAndView createGroup(@Valid ProductGroup productGroup, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  ProductGroup productGroupExists = productGroupRepository.findByName(productGroup.getName());
  
  if(productGroupExists != null) {
   bindingResult.rejectValue("name", "error.productGroup", "Ova kategorija već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("admin/create_group");
  } else {
	  productGroupRepository.save(productGroup);
   model.addObject("msg", "Nova kategorija je uspješno kreirana!");
   model.addObject("productGroup", new ProductGroup());
   model.setViewName("admin/create_group");
  }
  return model;
 }
 
 @RequestMapping(value= {"admin/createmaker"}, method=RequestMethod.GET)
 public ModelAndView createMaker() {
  ModelAndView model = new ModelAndView();
  ProductMaker productMaker = new ProductMaker();
  model.addObject("productMaker", productMaker);
  model.setViewName("admin/create_maker");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createmaker"}, method=RequestMethod.POST)
 public ModelAndView createMaker(@Valid ProductMaker productMaker, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  ProductMaker productMakerExists = productMakerRepository.findByName(productMaker.getName());
  
  if(productMakerExists != null) {
   bindingResult.rejectValue("name", "error.productMaker", "Ovaj proizvođač već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("admin/create_maker");
  } else {
	  productMakerRepository.save(productMaker);
	  model.addObject("msg", "Novi proizvođać je uspješno kreiran!");
	  model.addObject("productMaker", new ProductMaker());
	  model.setViewName("admin/create_maker");
  	}
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct"}, method=RequestMethod.GET)
 public ModelAndView createProduct() {
  ModelAndView model = new ModelAndView();
  Product product = new Product();
  List<ProductGroup> productGroupList = productGroupRepository.findAll();
  List<ProductMaker> productMakerList = productMakerRepository.findAll();
  model.addObject("product", product);
  model.addObject("productMakerList", productMakerList);
  model.addObject("productGroupList", productGroupList);
  model.setViewName("admin/create_product");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct1"}, method=RequestMethod.POST)
 public ModelAndView createProduct(@Valid Product product, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product productExists = productRepository.findBySifra(product.getSifra());
  
  if(productExists != null) {
   bindingResult.rejectValue("sifra", "error.product", "Ova šifra već postoji!");
  }
  if(bindingResult.hasErrors()) {
	  List<ProductGroup> productGroupList = productGroupRepository.findAll();
	  List<ProductMaker> productMakerList = productMakerRepository.findAll();
	  model.addObject("product", product);
	  model.addObject("productMakerList", productMakerList);
	  model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  } else {
   //model.addObject("msg", "Novi proizvođać je uspješno kreiran!");

	  ProductGroup productGroup = product.getProductGroup();
	  productGroupRepository.save(productGroup);
	  
	  ProductMaker productMaker = product.getProductMaker();
	  productMakerRepository.save(productMaker);
	  
	  productRepository.save(product);

	  List<Product> productList = productServiceImpl.findProductsByGroup(productGroup);
   
	  Long productId = product.getId();
	  Product savedProduct = productRepository.findById(productId).get();
	  
	  model.addObject("productList", productList);

   model.addObject("product", savedProduct);
   model.setViewName("admin/create_product2");
  }
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct2"}, method=RequestMethod.POST)
 public ModelAndView createProduct2(@Valid Product product, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Set<Product> replaceProducts = new HashSet<Product>();
  	if(product.getProducts() == null) {
  		product.setProducts(replaceProducts);
  	} else {
  		replaceProducts = product.getProducts();
  	}
  	   Product savedProduct = productRepository.findById(product.getId()).get();
  	   
  	   System.out.println("Product content ID " + product.getId());
  	   System.out.println("Product content ID " + product.getProducts());
  	   
  	   savedProduct.setProducts(replaceProducts);
  	   productRepository.save(savedProduct);
  	   model.addObject("msg", "Novi auto dio je uspješno kreiran!");
   
   List<ProductGroup> productGroupList = productGroupRepository.findAll();
   List<ProductMaker> productMakerList = productMakerRepository.findAll();
   model.addObject("product", new Product());
   model.addObject("productMakerList", productMakerList);
   model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  return model;
 }
 /*
 @RequestMapping(value= {"/admin/admin"}, method=RequestMethod.GET)
 public ModelAndView adminPage() {
  ModelAndView model = new ModelAndView();
  model.setViewName("admin/adminPage");
  return model;
 }
 
 @RequestMapping(value= {"/home/home"}, method=RequestMethod.GET)
 public ModelAndView home() {
  ModelAndView model = new ModelAndView();
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  User user = userService.findUserByEmail(auth.getName());
  
  model.addObject("userName", user.getFirstname() + " " + user.getLastname());
  model.setViewName("home/home");
  return model;
 }
 
 @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
 public ModelAndView accessDenied() {
  ModelAndView model = new ModelAndView();
  model.setViewName("errors/access_denied");
  return model;
 }
 */
}