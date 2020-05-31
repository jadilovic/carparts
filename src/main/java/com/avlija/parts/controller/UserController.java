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

import com.avlija.parts.model.Product;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.repository.TransactionRepository;
import com.avlija.parts.service.UserService;

@Controller
public class UserController {

 @Autowired
 private UserService userService;
 
 @Autowired
 private TransactionRepository transactionRepository;
 
 @Autowired
 private ProductRepository productRepository;
 
 
 @RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  
  model.setViewName("user/login");
  return model;
 }
 
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
   model.setViewName("admin/signup");
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
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  User user = userService.findUserByEmail(auth.getName());
  
  if(user == null) {
	  user = new User();
	  user.setFirstname("TEST");
	  user.setLastname("USER");
  }
  
  model.addObject("userName", user.getFirstname() + " " + user.getLastname());
  model.setViewName("admin/adminPage");
  return model;
 }
 
 @RequestMapping(value= {"/home/home"}, method=RequestMethod.GET)
 public ModelAndView home() {
  ModelAndView model = new ModelAndView();
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  User user = userService.findUserByEmail(auth.getName());
  
  if(user == null) {
	  user = new User();
	  user.setFirstname("TEST");
	  user.setLastname("USER");
  }
  
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
 
 @RequestMapping(value= {"/user/guest"}, method=RequestMethod.GET)
 public ModelAndView guestPage() {
  ModelAndView model = new ModelAndView();
  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  User user = userService.findUserByEmail(auth.getName());
  
  if(user == null) {
	  user = new User();
	  user.setFirstname("TEST");
	  user.setLastname("USER");
  }
  
  model.addObject("userName", user.getFirstname() + " " + user.getLastname());
  model.setViewName("user/guestPage");
  return model;
 }
 
 @RequestMapping(value= {"/user/alltransactions"}, method=RequestMethod.GET)
 public ModelAndView listReplaceProducts() {
	 List <Transaction> transactionsList = (List<Transaction>) transactionRepository.findAll();
	 String message2 = null;
	 if(transactionsList.size() == 0) {
		 message2 = "Nema transakcija";
	 } else {
		 message2 = "List svih transakcija";
	 }
  ModelAndView model = new ModelAndView();
  model.addObject("message2", message2);
  model.addObject("transactionsList", transactionsList);
  model.setViewName("user/list_transactions");
  return model;
 }
 
 @RequestMapping(value= {"/user/prodtransactions/{id}"}, method=RequestMethod.GET)
 public ModelAndView editProduct(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  List <Transaction> transactionsList = transactionRepository.findByProduct(product);
  
	 String message2 = null;
	 if(transactionsList.size() == 0) {
		 message2 = "Nema transakcija";
	 } else {
		 message2 = "List svih transakcija za proizvod " + product.getName() + " šifre: " + product.getSifra();
	 }
	 
  model.addObject("message2", message2);
  model.addObject("transactionsList", transactionsList);
  model.setViewName("user/list_transactions");
  return model;
 }
}