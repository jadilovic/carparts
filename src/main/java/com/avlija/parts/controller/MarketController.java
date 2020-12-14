package com.avlija.parts.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.Post;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.BrandRepository;
import com.avlija.parts.repository.PostRepository;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.repository.TransactionRepository;
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
	 private PostRepository postRepository;
	 
	 @Autowired
	 private ProductGroupRepository productGroupRepository;
	 
	 private static List<Post> postsBySifra;
	 private static List<Post> listOfPostsWithProductGroup;

	 
	 @RequestMapping(value= {"/user/searchposts"}, method=RequestMethod.GET)
	 public ModelAndView postInfo()  {
		 ModelAndView model = new ModelAndView();
		 SampleInputs sampleInputs = new SampleInputs();
		 model.addObject("sampleInputs", sampleInputs);
		 model.setViewName("user/search_posts");
	  	   return model;
	 }
	 
	 // Displaying info about the post by ID
	 @RequestMapping(value= {"/user/postinfo/{id}"}, method=RequestMethod.GET)
	 public ModelAndView postInfo(@PathVariable(name = "id") Integer id)  {
		 Post post = postRepository.findById(id).get();
		 ModelAndView model = new ModelAndView();
	   		
		 model.addObject("msg", "Info o objavljenom oglasu");
		 model.addObject("post", post);
		 model.setViewName("user/post_info");
	  	   return model;
	 }
	 
	 // Searching for post by sifra
	 @RequestMapping(value= {"/user/postsearch"}, method=RequestMethod.POST)
	 public String searchPostsBySifra(@Valid SampleInputs sampleInputs, HttpServletRequest request) {
		 postsBySifra = postRepository.findByProductSifra(sampleInputs.getSifra());
		 return "redirect:/user/displayposts";
	 }
	 
	 // Displaying found posts by sifra
	 @RequestMapping(value= {"/user/displayposts"}, method=RequestMethod.GET)
	 public ModelAndView displayPosts(HttpServletRequest request) {
		 ModelAndView model = new ModelAndView();
		 if(postsBySifra.isEmpty()) {
			 SampleInputs sampleInputs = new SampleInputs();
			 model.addObject("sampleInputs", sampleInputs);
			   model.setViewName("user/search_posts");
			   model.addObject("msg", "Nije pronađen oglas sa unesenom šifrom. Pokušajte ponovo.");
		 } else {
		  	   
		       int page = 0; //default page number is 0
		       int size = 10; //default page size is 10
		       
		       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
		           page = Integer.parseInt(request.getParameter("page")) - 1;
		       }

		       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
		           size = Integer.parseInt(request.getParameter("size"));
		       }
		       
		       String productSifra = postsBySifra.get(0).getProductSifra();

		       Page <Post> postsList = null;
		   		postsList = postRepository.findByProductSifra(productSifra, PageRequest.of(page, size, Sort.by("created").descending()));
		   	
		   		String message = null;
		   		if(postsList == null) {
		   			message = "Nema objavljenih oglasa";
		   		}
		   		
		   	message = "Rezultat pretrage po šifri: '" + productSifra + "'";
		   	model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("user/all_product_posts");
		 }
	  	   return model;
	 }
	 
	 // Searching for post by ID
	 @RequestMapping(value= {"/user/postinfo2"}, method=RequestMethod.POST)
	 public ModelAndView searchPost2(@Valid SampleInputs sampleInputs) {
		 ModelAndView model = new ModelAndView();
		 try {
			 Post postByID = postRepository.findById(sampleInputs.getPostId()).get();	
		  	   model.addObject("post", postByID);
		  	   model.addObject("msg", "Rezultat pretrage po broju '" + sampleInputs.getPostId() + "' objavljenog oglasa");
		  	   model.setViewName("user/post_info");
		 } catch(Exception e) {
			   model.setViewName("user/search_posts");
			   model.addObject("msg", "Nije pronađen oglas sa unesenim ID brojem. Pokušajte ponovo.");
		 }
	  	   return model;
	 }
	 
	 // Searching for posts by product group
	 @RequestMapping(value= {"/user/postgroupsearch/{productGroupId}"}, method=RequestMethod.GET)
	 public String searchPostsByProductGroup(@PathVariable(name = "productGroupId") Long productGroupId) {
		 List<Post> listOfAllPosts = postRepository.findAll();
		 
		 listOfPostsWithProductGroup = new ArrayList<>();
		 for(Post post: listOfAllPosts) {
			 Product product = productRepository.findById(post.getProductId()).get();
			 Long prdGroupId = product.getProductGroup().getId();
			 if(prdGroupId == productGroupId) {
				 listOfPostsWithProductGroup.add(post);
			 }
		 }

		 return "redirect:/user/displaypostsgroup";
	 }
	 
	 // Displaying found posts by product group ID
	 @RequestMapping(value= {"/user/displaypostsgroup"}, method=RequestMethod.GET)
	 public ModelAndView displayPostsByProductGroup(HttpServletRequest request) {
		 ModelAndView model = new ModelAndView();
		 if(listOfPostsWithProductGroup.isEmpty()) {
			 SampleInputs sampleInputs = new SampleInputs();
			 model.addObject("sampleInputs", sampleInputs);
			   model.setViewName("user/search_posts");
			   model.addObject("msg", "Nisu pronađeni oglasi odabrane kategorije. Pokušajte ponovo.");
		 } else {
		  	   
		       int page = 0; //default page number is 0
		       int size = 10; //default page size is 10
		       
		       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
		           page = Integer.parseInt(request.getParameter("page")) - 1;
		       }

		       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
		           size = Integer.parseInt(request.getParameter("size"));
		       }
		       
		       Long producId = listOfPostsWithProductGroup.get(0).getProductId();
		       Product product = productRepository.findById(producId).get();
		       Long productGroupId = product.getProductGroup().getId();
		       
		       Page <Post> postsList = postRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));
				 List<Post> postsListL = postsList.toList();
		       for(Post post: postsListL) {
					 Product postProduct = productRepository.findById(post.getProductId()).get();
					 Long prdGroupId = postProduct.getProductGroup().getId();
					 if(prdGroupId != productGroupId) {
						 postsListL.remove(post);
					 }
				 }
	
		       final Page<Post> pageList = new PageImpl<>(postsListL);

		   		String message = null;
		   		if(postsList.isEmpty()) {
		   			message = "Nema objavljenih oglasa";
		   		}
		   		
		   	message = "Rezultat pretrage po grupi.";
		   	model.addObject("message", message);
	  	   model.addObject("postsList", pageList);
	  	   model.setViewName("user/all_product_posts");
		 }
	  	   return model;
	 }


	// Publishing new post on the market
	 @RequestMapping(value= {"/user/market/{id}"}, method=RequestMethod.GET)
	 public ModelAndView marketPost(@PathVariable(name = "id") Long id) {
	  ModelAndView model = new ModelAndView();
	  Product product = productRepository.findById(id).get();
	  User user = getCurrentUser();
	  List<Product> replaceProducts = product.getProducts();
	  ProductQuantity productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
	  		
	  if(productQuantity.getQuantity() <= 0) {
		  model.addObject("product", product);
		  model.addObject("productQuantity", productQuantity);
		  model.addObject("replaceProducts", replaceProducts);
			model.addObject("err", "Nije moguce objaviti u oglasniku jer nemate proizvod na stanju");
  			model.setViewName("home/product_profile");
	  } else {
		  Post post = new Post();
		  post.setActive(1);
		  post.setProductId(product.getId());
		  post.setUserId(user.getId());
		  post.setUserName(user.getFirstname());
		  post.setProductSifra(product.getSifra());
		  post.setProductName(product.getName());
		  post.setMaxAvailable(productQuantity.getQuantity());
	  			model.addObject("post", post);
	  			model.addObject("msg", "Objava oglasa. Popunite prazna polja!");
	  			model.setViewName("user/publish_post");
	  		}
	  return model;
	 }
	 
	 // Saving new published post on the market
	 @RequestMapping(value= {"/user/market"}, method=RequestMethod.POST)
	 public String publishPost(@Valid Post post, HttpServletRequest request) {
	  	   post.setCreated(new Date());
	  	   postRepository.save(post);
	  	   int id = post.getId();

	  	   return "redirect:/user/postinfo/" + id;
	 }
	 
	 // Display all user posts
	 @GetMapping(value= {"/user/userposts"})
	 public ModelAndView listUserPosts(HttpServletRequest request) {
	  ModelAndView model = new ModelAndView();
	  	   
	       int page = 0; //default page number is 0
	       int size = 10; //default page size is 10
	       
	       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
	           page = Integer.parseInt(request.getParameter("page")) - 1;
	       }

	       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
	           size = Integer.parseInt(request.getParameter("size"));
	       }
	       
	       User user = getCurrentUser();
	       int userId = user.getId();
	       Page <Post> postsList = null;
	   		postsList = postRepository.findByUserId(userId, PageRequest.of(page, size, Sort.by("created").descending()));

	   		String message = null;
	   		if(postsList == null) {
	   			message = "Nemate objavljenih oglasa";
	   		}
	   		
	   		model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("user/user_posts");
	  	   return model;
	 }
	 
	 // Displaying all posts on the market
	 @GetMapping(value= {"/home/allposts"})
	 public ModelAndView listAllPosts(HttpServletRequest request) {
	  ModelAndView model = new ModelAndView();
	  	   
	       int page = 0; //default page number is 0
	       int size = 10; //default page size is 10
	       
	       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
	           page = Integer.parseInt(request.getParameter("page")) - 1;
	       }

	       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
	           size = Integer.parseInt(request.getParameter("size"));
	       }

	       Page <Post> postsList = null;
	   		postsList = postRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));

	   		String message = null;
	   		if(postsList == null) {
	   			message = "Nema objavljenih oglasa";
	   		}
	   		
	   		model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("home/all_posts");
	  	   return model;
	 }
	 
	 private User getCurrentUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			return user;
	}
}
