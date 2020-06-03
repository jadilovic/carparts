package com.avlija.parts.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.avlija.parts.model.Role;
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
	 List <Transaction> transactionsList = (List<Transaction>) transactionRepository.findByOrderByCreatedDesc();
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
  List <Transaction> transactionsList = transactionRepository.findByProductOrderByCreatedDesc(product);
  
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
 
 @RequestMapping("/user/profile")
 public ModelAndView profilePage() {
     ModelAndView mav = new ModelAndView("user/profile_page");
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     User userProfile = userService.findUserByEmail(auth.getName());
     Set <Role> roles = userProfile.getRoles();
     mav.addObject("roles", roles);
     mav.addObject("userProfile", userProfile);
     return mav;
 }

 @RequestMapping(value = "/user/alltransactions2/{page}")
 public ModelAndView listArticlesPageByPage(@PathVariable("page") int page) {
     ModelAndView modelAndView = new ModelAndView("user/list_transactions2");
     PageRequest pageable = PageRequest.of(page - 1, 15);
     Page<Transaction> transactionsPage = transactionRepository.findAll(pageable);
     int totalPages = transactionsPage.getTotalPages();
     if(totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
         modelAndView.addObject("pageNumbers", pageNumbers);
     }
     modelAndView.addObject("activeArticleList", true);
     modelAndView.addObject("transactionsList", transactionsPage.getContent());
     return modelAndView;
 }

}