package com.avlija.parts.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.model.ProductMaker;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.service.ProductServiceImpl;
import com.avlija.parts.service.UserService;

@Controller
public class SearchController {

 @Autowired
 private ProductRepository productRepository;
 
 @Autowired
 private ProductGroupRepository productGroupRepository;
 
 @Autowired
 private ProductServiceImpl productServiceImpl;
 
 @RequestMapping(value= {"/home/search"}, method=RequestMethod.GET)
 public ModelAndView search() {
  ModelAndView model = new ModelAndView();
  model.addObject("sampleInputs", new SampleInputs());
  model.setViewName("home/search");
  return model;
 }
 
 @RequestMapping(value= {"/home/ulja"}, method=RequestMethod.GET)
 public ModelAndView ulja() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_ulja");
  return model;
 }
 
 @RequestMapping(value= {"/home/uljamotorna"}, method=RequestMethod.GET)
 public ModelAndView uljaMotorna() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_ulje_motorno");
  return model;
 }
 
 @RequestMapping(value= {"/home/listproducts/{productGroupId}"}, method=RequestMethod.GET)
 public ModelAndView listProducts(@PathVariable(name = "productGroupId") Long productGroupId) {
	 ProductGroup productGroup = productGroupRepository.findById(productGroupId).get();
	 List<Product> productList = productServiceImpl.findProductsByGroup(productGroup);
  ModelAndView model = new ModelAndView();
  model.addObject("message", productGroup.getName());
  model.addObject("productList", productList);
  model.setViewName("home/list_products");
  return model;
 }
 
 @RequestMapping(value= {"/home/listreplaceproducts/{id}"}, method=RequestMethod.GET)
 public ModelAndView listReplaceProducts(@PathVariable(name = "id") Long id) {
	 Product product = productRepository.findById(id).get();
	 Set <Product> productList = product.getProducts();
	 String message2 = null;
	 if(productList.size() == 0) {
		 message2 = "Nema zamjenskog dijela";
	 } else {
		 message2 = "Zamjenski dijelovi za šifru " + product.getSifra();
	 }
  ModelAndView model = new ModelAndView();
  model.addObject("message", product.getProductGroup().getName());
  model.addObject("message2", message2);
  model.addObject("productList", productList);
  model.setViewName("home/list_products");
  return model;
 }
 
 @RequestMapping(value= {"/home/productprofile/{id}"}, method=RequestMethod.GET)
 public ModelAndView productProfile(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  String msg = null;
  
  if(product == null) {
	  msg = "Nije pronađen proizvod sa zadanim ID brojem";
	  model.addObject("msg", msg);
  } else {
	  Set<Product> replaceProducts = product.getProducts();
	  model.addObject("replaceProducts", replaceProducts);
	  model.addObject("msg", "Pregled profila traženog proizvoda!");
	  model.addObject("product", product);
	  model.setViewName("home/product_profile");
  	}
  return model;
 }
 
 @RequestMapping(value= {"/home/productprofile"}, method=RequestMethod.POST)
 public ModelAndView productProfile(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findBySifra(sampleInputs.getSifra());
  
  if(product == null) {
   bindingResult.rejectValue("sifra", "error.sampleInputs", "Nije pronađen proizvod zadane šifre!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("home/search");
  } else {
	  Set<Product> replaceProducts = product.getProducts();
	  model.addObject("replaceProducts", replaceProducts);
	  model.addObject("msg", "Pregled profila traženog proizvoda!");
	  model.addObject("product", product);
	  model.setViewName("home/product_profile");
  	}
  return model;
 }
 /*
 @RequestMapping(value= {"admin/signup"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  User user = new User();
  model.addObject("user", user);
  model.setViewName("admin/signup");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/signup"}, method=RequestMethod.POST)
 public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  User userExists = userService.findUserByEmail(user.getEmail());
  
  if(userExists != null) {
   bindingResult.rejectValue("email", "error.user", "This email already exists!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("user/signup");
  } else {
   userService.saveUser(user);
   model.addObject("msg", "User has been registered successfully!");
   model.addObject("user", new User());
   model.setViewName("admin/signup");
  }
  return model;
 }
 
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