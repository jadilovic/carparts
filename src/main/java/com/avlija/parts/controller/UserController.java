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

import com.avlija.parts.model.Logins;
import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.Role;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.LoginsRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.repository.TransactionRepository;
import com.avlija.parts.repository.UserRepository;
import com.avlija.parts.service.UserService;

@Controller
public class UserController {

 @Autowired
 private UserService userService;
 
 @Autowired
 private UserRepository userRepository;
 
 @Autowired
 private TransactionRepository transactionRepository;
 
 @Autowired
 private ProductRepository productRepository;
 
 @Autowired
 private ProductQuantityRepository productQuantityRepository;
 
 @Autowired
 private LoginsRepository loginsRepository;
 
 private static int userId;
 
 // Starting page of the application
 @RequestMapping(value= {"/", "/login"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  
  model.setViewName("user/login");
  return model;
 }
 
 // Displaying page for the guest to sign up
 @RequestMapping(value= {"/guestsignup"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  User user = new User();
  model.addObject("user", user);
  model.setViewName("user/guest_signup");
  
  return model;
 }
 
 // Submitting data for guest account registration and checking if email exists
 // Checking if password is valid
 // Creating guest user and saving data in the database
 @RequestMapping(value= {"/guestsignup"}, method=RequestMethod.POST)
 public ModelAndView createGuestUser(@Valid User user, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  User userExists = userService.findUserByEmail(user.getEmail());
  
  if(userExists != null) {
	  	bindingResult.rejectValue("email", "error.user", "Ovaj email već postoji!");
	  	model.setViewName("user/guest_signup");
  } else if(!user.getPassword().matches("[a-zA-Z0-9]*") || user.getPassword().length() < 8 || user.getPassword().length() > 14) {
		model.addObject("error", "Oops! Lozinka mora da sadrži slova i brojeve, i ne smije biti kraća od 8 karaktera i duža od 14 karaktera.");
		model.setViewName("user/guest_signup");	
	} else {
	  Date date = new Date();
	  user.setCreated(date);
	  userService.saveUser(user);
   
	  model.addObject("msg", "Gost korisnički profil je uspješno kreiran. \nMožete se prijaviti kao gost!");
	  model.addObject("user", new User());
	  model.setViewName("user/login");
	}
  return model;
 }
 
 // Starting registration of new user by Admin, which allows registration for GUEST, CLIENT and ADMIN
 @RequestMapping(value= {"/admin/signup"}, method=RequestMethod.GET)
 public ModelAndView adminSignup() {
  ModelAndView model = new ModelAndView();
  User user = new User();
  model.addObject("user", user);
  model.setViewName("admin/signup");
  
  return model;
 }
 
 // Submitting data for any user account by ADMIN, registration of new user and checking if email exists
 // Checking if password is valid
 // Creating GUEST, CLIENT or ADMIN user and saving data in the database
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
	  Date date = new Date();
	  user.setCreated(date);
   userService.saveUser(user);
   user = userService.findUserByEmail(user.getEmail());
   Set<Role> roles = user.getRoles();
   for(Role role: roles) {
	   if(role.getRole().equals("CLIENT")) {
		   List<Product> productsList = (List<Product>) productRepository.findAll();
		   productsAddedToNewUser(productsList, user);
	   }
   }
   model.addObject("msg", "User has been registered successfully!");
   model.addObject("user", new User());
   model.setViewName("admin/signup");
  }
  return model;
 }

 // Admin page for ADMIN users only
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

// Client page, only ADMIN and CLIENT users can access this page
@RequestMapping(value= {"/user/client"}, method=RequestMethod.GET)
public ModelAndView clientPage() {
 ModelAndView model = new ModelAndView();

 User user = getCurrentUser();
 
 if(user == null) {
	  user = new User();
	  user.setFirstname("TEST");
	  user.setLastname("USER");
 }
 
 model.addObject("userName", user.getFirstname() + " " + user.getLastname());
 model.setViewName("user/clientPage");
 return model;
}

// Home page for ADMIN and CLIENT users
// Guest users are redirected to guest page automatically
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
  Set<Role> roles = user.getRoles();
  for(Role role: roles) {
	   if(role.getRole().equals("GUEST")) {
			 model.setViewName("user/guestPage");
	   } else {
			 model.setViewName("home/home");
	   }
  }
  return model;
 }
 
// Displaying access denied page every time the user clicks on the link to the page for which he has no access
 @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
 public ModelAndView accessDenied() {
  ModelAndView model = new ModelAndView();
  model.setViewName("errors/access_denied");
  return model;
 }
 
 // Displaying guest page for the user
 @RequestMapping(value= {"/home/guest"}, method=RequestMethod.GET)
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
 
 // Display user profile
 @RequestMapping("/home/profile")
 public ModelAndView profilePage() {
     ModelAndView mav = new ModelAndView("user/profile_page");
     User userProfile = getCurrentUser();
     Set <Role> roles = userProfile.getRoles();
     mav.addObject("roles", roles);
     mav.addObject("userProfile", userProfile);
     return mav;
 }
 
 // Start searching for users by ADMIN
 @RequestMapping("/admin/searchusers")
 public ModelAndView searchUsers() {
     ModelAndView mav = new ModelAndView("admin/search_users");
     SampleInputs sampleInputs = new SampleInputs();
     mav.addObject("sampleInputs", sampleInputs);
     return mav;
 }
 
 // Entering user email to search users by ADMIN
 @RequestMapping(value= {"admin/searchemail"}, method=RequestMethod.POST)
 public ModelAndView searchUserByEmail(@Valid SampleInputs sampleInputs) {
     ModelAndView mav = new ModelAndView();
     try {
    	 User userProfile = userRepository.findByEmail(sampleInputs.getEmail());
    	 Set <Role> roles = userProfile.getRoles();
    	 mav.addObject("roles", roles);
    	 mav.addObject("userProfile", userProfile);
    	 mav.setViewName("user/profile_page");
     		} catch (Exception e) {
     			mav.setViewName("admin/search_users");
     			mav.addObject("msg", "Unesena email adresa klijenta ne postoji. Pokušajte ponovo.");
     		}
     return mav;
 }
 
 // After back button is clicked return back beginning of searching users
 @RequestMapping(value= {"admin/searchemail"}, method=RequestMethod.GET)
 public String backToUsersSearch() {
	 return "redirect:/admin/searchusers";
 }
 
 // Searching for user by entered user ID by ADMIN
 @RequestMapping(value= {"admin/searchid"}, method=RequestMethod.POST)
 public ModelAndView searchUserById(@Valid SampleInputs sampleInputs) {
     ModelAndView mav = new ModelAndView();
     try {
    	 User userProfile = userRepository.findById(sampleInputs.getUserId()).get();
    	 Set <Role> roles = userProfile.getRoles();
    	 mav.addObject("roles", roles);
    	 mav.addObject("userProfile", userProfile);
    	 mav.setViewName("user/profile_page");
     		} catch (Exception e) {
     			mav.setViewName("admin/search_users");
     			mav.addObject("msg", "Uneseni ID broj klijenta ne postoji. Pokušajte ponovo.");
     		}
     return mav;
 }
 
 // After back button is clicked return back beginning of searching users
 @RequestMapping(value= {"admin/searchid"}, method=RequestMethod.GET)
 public String backToSearchUsers() {
	 return "redirect:/admin/searchusers";
 }
 
 // Display of user transactions for certain product, for ADMIN display of all transactions by all users
 @RequestMapping(value= {"/user/prodtransactions/{id}"}, method=RequestMethod.GET)
 public ModelAndView productTransactions(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  User user = getCurrentUser();
  List<Transaction> transactionsList = new ArrayList<>();
  Set<Role> roles = user.getRoles();
  for(Role role: roles) {
	  if(role.getRole().equals("ADMIN")) {
		  transactionsList = transactionRepository.findFirst30ByProductOrderByCreatedDesc(product);
	  } else {
		  	transactionsList = transactionRepository.findFirst30ByProductAndUserOrderByCreatedDesc(product, user);
	  		}
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
 
 // Display of all user transactions / for ADMIN display of all transactions for all users
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
     Page <Transaction> transactionsList = null;
     Set<Role> roles = user.getRoles();
     for(Role role: roles) {
   	  if(role.getRole().equals("ADMIN")) {
   		  transactionsList = transactionRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));
   	  	} else {
   		  transactionsList = transactionRepository.findByUser(user, PageRequest.of(page, size, Sort.by("created").descending()));
   	  	}
     }    
     model.addAttribute("message", "za sve artikle.");
     model.addAttribute("transactions", transactionsList);
     return "user/list_transactions2";
 }
 
 // Display of all users, only accessible by ADMIN
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
 
 // Selecting certain user to edit its profile
 @RequestMapping(value= {"admin/editprofile/{id}"}, method=RequestMethod.GET)
 public ModelAndView editUserProfile(@PathVariable(name = "id") Integer id) {
  ModelAndView model = new ModelAndView();
  User user = userRepository.findById(id).get();
  Set<Role> roles = user.getRoles();
  for(Role role: roles)
	  user.setRole(role.getRole());
  model.addObject("user", user);
  model.setViewName("admin/edit_user");
  return model;
 }
 
 // Entering new values for the selected user profile to be edited and updating edits
 @RequestMapping(value= {"admin/editprofile"}, method=RequestMethod.POST)
 public ModelAndView editProduct(@Valid User user) {
  ModelAndView model = new ModelAndView();
  
  User changedUser = userRepository.findByEmail(user.getEmail());
  userId = changedUser.getId();
  changedUser.setFirstname(user.getFirstname());
  changedUser.setLastname(user.getLastname());
  changedUser.setRole(user.getRole());
  changedUser.setCountry(user.getCountry());
  changedUser.setActive(user.getActive());
  changedUser.setPassword(user.getPassword());
  userService.updateUser(changedUser);
  
  Set<Role> roles = changedUser.getRoles();
  for(Role role: roles) {
	   if(role.getRole().equals("CLIENT")) {
		   List<Product> productsList = (List<Product>) productRepository.findAll();
		   productsAddedToNewUser(productsList, changedUser);
	   }
  }
	  model.addObject("msg", "Izmjena podataka profila korisnika izvrsena");
	  model.addObject("userProfile", changedUser);
	  model.addObject("roles", roles);
	  model.setViewName("user/profile_page");
  return model;
 }
 
//After back button is clicked return back to user profile
@RequestMapping(value= {"admin/editprofile"}, method=RequestMethod.GET)
public String backToUserProfile() {
	 return "redirect:/admin/editprofile/" + userId;
}
 
 // Display of currently logged users for ADMIN only
 @RequestMapping(value= {"/admin/loggedUsers"}, method=RequestMethod.GET)
 public ModelAndView loggedUsers() {
  ModelAndView model = new ModelAndView();
  List<Logins> users = (List<Logins>) loginsRepository.findAll();
  if(users.size() == 0) {
	  String message = "No users are logged in";
	  model.addObject("message", message);
  }
  model.addObject("users", users);
  model.setViewName("admin/users");
  
  return model;
 }
 
 // Deleting all logged users in the app by ADMIN
 @RequestMapping(value= {"/admin/deleteLoggedUsers"}, method=RequestMethod.GET)
 public ModelAndView deleteLoggedUsers() {
  ModelAndView model = new ModelAndView();
  loginsRepository.deleteAll();
  List<Logins> users = new ArrayList<>();
  model.addObject("message", "All loggins were deleted");
  model.addObject("users", users);
  model.setViewName("admin/users");
  return model;
 }
 
 // Deleting one selected logged user in the app by ADMIN
 @RequestMapping(value= {"admin/deleteLoggedUser/{id}"}, method=RequestMethod.GET)
 public ModelAndView deleteLoggedUser(@PathVariable(name = "id") String id) {
  ModelAndView model = new ModelAndView();
  loginsRepository.deleteById(id);
  List<Logins> users = (List<Logins>) loginsRepository.findAll();
  if(users.size() == 0) {
	  String message = "No users are logged in";
	  model.addObject("message", message);
  }
  model.addObject("users", users);
  model.setViewName("admin/users");
  return model;
 }
 
 // Adding product quantity to the user with CLIENT permission, line 394 of this controller
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
 
 // Finding current user for different purposes
 private User getCurrentUser() {
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  User user = userService.findUserByEmail(auth.getName());
	return user;
}
}