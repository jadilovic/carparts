package com.avlija.parts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.Role;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.ProductQuantityRepository;
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
 
 @Autowired
 ProductQuantityRepository productQuantityRepository;
 
 
 @RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  
  model.setViewName("user/login");
  return model;
 }
 
 @RequestMapping(value= {"/guestsignup"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  User user = new User();
  model.addObject("user", user);
  model.setViewName("user/guest_signup");
  
  return model;
 }
 
 @RequestMapping(value= {"/guestsignup"}, method=RequestMethod.POST)
 public ModelAndView createGuestUser(@Valid User user, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  User userExists = userService.findUserByEmail(user.getEmail());
  
  if(userExists != null) {
   bindingResult.rejectValue("email", "error.user", "Ovaj email već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("user/guest_signup");
  } else {
	  Date date = new Date();
	  user.setCreated(date);
   userService.saveUser(user);
   user = userService.findUserByEmail(user.getEmail());
   List<Product> productsList = (List<Product>) productRepository.findAll();
   productsAddedToNewUser(productsList, user);
   
   model.addObject("msg", "Gost korisnički profil je uspješno kreiran. Možete se prijaviti kao gost!");
   model.addObject("user", new User());
   model.setViewName("user/login");
  }
  return model;
 }
 
 @RequestMapping(value= {"/admin/signup"}, method=RequestMethod.GET)
 public ModelAndView adminSignup() {
  ModelAndView model = new ModelAndView();
  User user = new User();
  model.addObject("user", user);
  model.setViewName("admin/signup");
  
  return model;
 }
 
 @RequestMapping(value= {"/admin/signup"}, method=RequestMethod.POST)
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
   //user = userService.findUserByEmail(user.getEmail());
   //List<Product> productsList = (List<Product>) productRepository.findAll();
   //productsAddedToNewUser(productsList, user);
   
   model.addObject("msg", "User has been registered successfully!");
   model.addObject("user", new User());
   model.setViewName("admin/signup");
  }
  return model;
 }

@RequestMapping(value= {"/admin/admin"}, method=RequestMethod.GET)
 public ModelAndView adminPage() {
  ModelAndView model = new ModelAndView();

  User user = getCurrentUser();
  
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
 
 @RequestMapping("/user/profile")
 public ModelAndView profilePage() {
     ModelAndView mav = new ModelAndView("user/profile_page");
     User userProfile = getCurrentUser();
     Set <Role> roles = userProfile.getRoles();
     mav.addObject("roles", roles);
     mav.addObject("userProfile", userProfile);
     return mav;
 }
 
 @RequestMapping(value= {"/user/prodtransactions/{id}"}, method=RequestMethod.GET)
 public ModelAndView editProduct(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  User user = getCurrentUser();
  List<Transaction> transactionsList = new ArrayList<>();
  if(user.getRole() == "ADMIN") {
	  transactionsList = transactionRepository.findFirst30ByProductOrderByCreatedDesc(product);
  } else {
	  transactionsList = transactionRepository.findFirst30ByProductAndUserOrderByCreatedDesc(product, user);
  }
	 String message2 = null;
	 if(transactionsList.size() == 0) {
		 message2 = "Nema transakcija";
	 } else {
		 message2 = "Lista 30 posljednjih transakcija za proizvod " + product.getName() + " šifre: " + product.getSifra();
	 }
	 
  model.addObject("message2", message2);
  model.addObject("transactionsList", transactionsList);
  model.setViewName("user/list_transactions");
  return model;
 }
 
 @GetMapping("/user/alltransactions")
 public String customersPage(HttpServletRequest request, Model model) {
     
     int page = 0; //default page number is 0
     int size = 10; //default page size is 10
     
     if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
         page = Integer.parseInt(request.getParameter("page")) - 1;
     }

     if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
         size = Integer.parseInt(request.getParameter("size"));
     }
     
     User user = getCurrentUser();
     
     Page<Transaction> transactionsList = transactionRepository.findByUser(user, PageRequest.of(page, size, Sort.by("created").descending()));
     
     model.addAttribute("message", "za sve artikle.");
     model.addAttribute("transactions", transactionsList);
     return "user/list_transactions2";
 }
 
 @RequestMapping(value= {"/admin/allusers"}, method=RequestMethod.GET)
 public ModelAndView showAllUsers() {
  ModelAndView model = new ModelAndView();
  List<User> listUsers = userService.findAllUsers();
  model.addObject("listUsers", listUsers);
  User user = new User();
  model.addObject("user", user);
  model.setViewName("admin/list_all_users");
  
  return model;
 }
 
 private void productsAddedToNewUser(List<Product> productsList, @Valid User user) {
	 List<ProductQuantity> productQuantitiyList = new ArrayList<ProductQuantity>();
	 for(Product product: productsList) {
		 ProductQuantity productQuantity;
		 try {
			 productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
		 } catch(Exception e) {
			 productQuantity = new ProductQuantity(new UserProduct(user.getId(), product.getId()), 0);
			 productQuantityRepository.save(productQuantity);
		 }
		 productQuantitiyList.add(productQuantity);
	 }
 	}
 
 private User getCurrentUser() {
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  User user = userService.findUserByEmail(auth.getName());
	return user;
}
}