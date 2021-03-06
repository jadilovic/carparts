package com.avlija.parts.controller;

import java.util.Collections;
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
import com.avlija.parts.repository.PostRepository;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.service.UserService;

@Controller
public class MarketController {
	
	 @Autowired
	 private ProductRepository productRepository;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private ProductQuantityRepository productQuantityRepository;
	 
	 @Autowired
	 private PostRepository postRepository;
	 
	 @Autowired
	 private ProductGroupRepository productGroupRepository;
	 
	 // Product keyword for searching posts
	 private static String productKeyword;
	 
	 // List of market posts by KeyWord
	 private static List<Post> postsByKeyword;
	 
	 // List of market posts by Sifra
	 private static List<Post> postsBySifra;
	 
	 // List market posts which belong to a certain product group
	 private static List<Post> listOfPostsWithProductGroup;
	 
	 // Finding current user to adjust the search for the country of the user
	 private static User currentUser;
	 
	 // Displaying all posts from all countries or only posts of user country
	 private static boolean allPosts;

	 // Starting page for searching market posts
	 @RequestMapping(value= {"/user/searchposts"}, method=RequestMethod.GET)
	 public ModelAndView postInfo()  {
		 ModelAndView model = new ModelAndView();
		 SampleInputs sampleInputs = new SampleInputs();
		 currentUser = getCurrentUser();
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
	 
	 // SEARCH POSTS BY KEYWORD
	 @RequestMapping(value= {"/user/keywordpostsearch"}, method=RequestMethod.POST)
	 public String searchPostsByKeyword(@Valid SampleInputs sampleInputs, HttpServletRequest request) {
		 productKeyword = sampleInputs.getProductKeyword();
		 postsByKeyword = postRepository.findByProductNameContainingAndCountryAndActive(productKeyword, currentUser.getCountry(), 1);
		 return "redirect:/user/displaykeywordposts";
	 }
	 
	 // DISPLAY POSTS FOUND BY KEYWORD
	 @RequestMapping(value= {"/user/displaykeywordposts"}, method=RequestMethod.GET)
	 public ModelAndView displayKeywordPosts(HttpServletRequest request) {
		 ModelAndView model = new ModelAndView();
		 if(postsByKeyword.isEmpty()) {
			 SampleInputs sampleInputs = new SampleInputs();
			 model.addObject("sampleInputs", sampleInputs);
			   model.setViewName("user/search_posts");
			   model.addObject("msg", "Nije pronađen oglas sa unesenom ključnom riječi. Pokušajte ponovo.");
		 } else {
		  	   
		       int page = 0; //default page number is 0
		       int size = 10; //default page size is 10
		       
		       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
		           page = Integer.parseInt(request.getParameter("page")) - 1;
		       }

		       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
		           size = Integer.parseInt(request.getParameter("size"));
		       }

		       Page <Post> postsList = null;
		   		postsList = postRepository.findByProductNameContainingAndCountryAndActive(productKeyword, currentUser.getCountry(), 1, PageRequest.of(page, size, Sort.by("created").descending()));
		   	
		   		String message = null;
		   		if(postsList == null) {
		   			message = "Nema objavljenih oglasa";
		   		}
		   		
		   	message = "Rezultat pretrage po ključnoj riječi: '" + productKeyword + "'";
		   	model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("user/all_keyword_posts");
		 }
	  	   return model;
	 }
	 
	 // Searching for post by sifra
	 @RequestMapping(value= {"/user/postsearch"}, method=RequestMethod.POST)
	 public String searchPostsBySifra(@Valid SampleInputs sampleInputs, HttpServletRequest request) {
		 postsBySifra = postRepository.findByProductSifraAndCountryAndActive(sampleInputs.getSifra(), currentUser.getCountry(), 1);
		 return "redirect:/user/displayposts";
	 }
	 
	 // Displaying posts found by sifra
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
		   		postsList = postRepository.findByProductSifraAndCountryAndActive(productSifra, currentUser.getCountry(), 1, PageRequest.of(page, size, Sort.by("created").descending()));
		   	
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
	 public ModelAndView searchPostById(@Valid SampleInputs sampleInputs) {
		 ModelAndView model = new ModelAndView();
		 Post postByID = postRepository.findByIdAndCountryAndActive(sampleInputs.getPostId(), currentUser.getCountry(), 1);	
		  	if(postByID != null) {
		  		model.addObject("post", postByID);
		  		model.addObject("msg", "Rezultat pretrage po broju '" + sampleInputs.getPostId() + "' objavljenog oglasa");
		  		model.setViewName("user/post_info");
		  	} else {
			   model.setViewName("user/search_posts");
			   model.addObject("msg", "Nije pronađen oglas sa unesenim ID brojem: '"
			   		+ sampleInputs.getPostId() + "'. Pokušajte ponovo.");
		  	}
	  	   return model;
	 }
	 
	 // After back button is clicked return back to post search
	 @RequestMapping(value= {"/user/postinfo2"}, method=RequestMethod.GET)
	 public String backSearchPosts() {
		 return "redirect:/user/searchposts";
	 }
	 
	 // Searching for posts by product group
	 @RequestMapping(value= {"/user/postgroupsearch/{groupId}"}, method=RequestMethod.GET)
	 public String searchPostsByProductGroup(@PathVariable(name = "groupId") Long groupId) {

		 listOfPostsWithProductGroup = postRepository.findByGroupIdAndCountryAndActive(groupId, currentUser.getCountry(), 1);
		 Collections.sort(listOfPostsWithProductGroup, Collections.reverseOrder());
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
		              
		       Page <Post> postsList = findPaginated(PageRequest.of(page, size));
		       
		   		String message = null;
		   		if(postsList.isEmpty()) {
		   			message = "Nema objavljenih oglasa";
		   		}
		   		
		   		Long groupId = listOfPostsWithProductGroup.get(0).getGroupId();
		   		ProductGroup productGroup = productGroupRepository.findById(groupId).get();
		   	message = "Rezultat pretrage po grupi: " + productGroup.getName();
		   	model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("user/all_group_posts");
		 }
	  	   return model;
	 }

	 // Converting Array List of posts by product group to Page Pageable
     public Page<Post> findPaginated(Pageable pageable) {
         int pageSize = pageable.getPageSize();
         int currentPage = pageable.getPageNumber();
         int startItem = currentPage * pageSize;
         List<Post> list;

         if (listOfPostsWithProductGroup.size() < startItem) {
             list = Collections.emptyList();
         } else {
             int toIndex = Math.min(startItem + pageSize, listOfPostsWithProductGroup.size());
             list = listOfPostsWithProductGroup.subList(startItem, toIndex);
         }

         Page<Post> postsPage
           = new PageImpl<Post>(list, PageRequest.of(currentPage, pageSize), listOfPostsWithProductGroup.size());
         //, Sort.by("created").descending()
         return postsPage;
     }

	// Creating - Publishing new post on the market based on product ID
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
		  post.setGroupId(product.getProductGroup().getId());
		  post.setUserId(user.getId());
		  post.setUserName(user.getFirstname());
		  post.setCountry(user.getCountry());
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
	  	   // Redirecting to line 72 of this Controller
	  	   return "redirect:/user/postinfo/" + id;
	 }
	 
	 // Display all posts by certain user
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
	 
	 // DISPLAY ALL POSTS
	 @RequestMapping(value= {"/home/allposts"}, method=RequestMethod.GET)
	 public String displayAllPosts() {
		 allPosts = true;
		 return "redirect:/home/posts";
	 }
	 
	 // DISPLAY ALL POSTS OF USER COUNTRY AND ACTIVE
	 @RequestMapping(value= {"/home/countryposts"}, method=RequestMethod.GET)
	 public String displayAllPostsByCountryAndActive() {
		 allPosts = false;
		 return "redirect:/home/posts";
	 }
	 
	 // Displaying all posts on the market
	 @GetMapping(value= {"/home/posts"})
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
	       if(allPosts) {
		   		postsList = postRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));
	       } else {
	    	   currentUser = getCurrentUser();
		   		postsList = postRepository.findByCountryAndActive(currentUser.getCountry(), 1, PageRequest.of(page, size, Sort.by("created").descending()));
	       }

	   		String message = null;
	   		if(postsList == null) {
	   			message = "Nema objavljenih oglasa";
	   		}
	   		
	   		model.addObject("message", message);
	  	   model.addObject("postsList", postsList);
	  	   model.setViewName("home/all_posts");
	  	   return model;
	 }
	 
	 // CHANGING THE STATUS OF THE POST
	 @RequestMapping(value= {"/user/postcompleted/{id}"}, method=RequestMethod.GET)
	 public ModelAndView changingActiveStatusOfPost(@PathVariable(name = "id") int id)  {
		 ModelAndView model = new ModelAndView();
		 currentUser = getCurrentUser();
		 Post post = postRepository.findById(id).get();
		 if(post.getUserId() == currentUser.getId()) {
			 if(post.getActive() == 0) {
				 post.setActive(1);
			  	 post.setCreated(new Date());
				 model.addObject("msg", "Oglas je obnovljen");
			 } else {
				 post.setActive(0);
				 model.addObject("err", "Oglas je završen");
			 }
			 postRepository.save(post);
		 } else {
			 model.addObject("err", "Nije dozvoljena izmjena statusa oglasa");
		 }
		 model.addObject("post", post);
		 model.setViewName("user/post_info");
	  	 return model;
	 }
	 
	 // Finding out user for the purpose of displaying user posts
	 private User getCurrentUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			return user;
	}
}
