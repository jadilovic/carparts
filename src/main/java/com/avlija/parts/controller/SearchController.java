package com.avlija.parts.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.form.SampleInputs;
import com.avlija.parts.model.Brand;
import com.avlija.parts.model.CarModel;
import com.avlija.parts.model.Post;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.BrandRepository;
import com.avlija.parts.repository.CarModelRepository;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
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
 
 @Autowired
 private BrandRepository brandRepository;
 
 @Autowired
 private CarModelRepository carModelRepository;
 
 @Autowired
 ProductQuantityRepository productQuantityRepository;
 
 @Autowired
 private UserService userService;
 
 // Finding list of auto parts
 private static List<Product> productList;
 
 // Finding quantity for each product in some list
 private static List<ProductQuantity> productQuantityList;
 
// Search page for starting searches by sifra and name
 @RequestMapping(value= {"/home/search"}, method=RequestMethod.GET)
 public ModelAndView search() {
  ModelAndView model = new ModelAndView();
  model.addObject("sampleInputs", new SampleInputs());
  model.setViewName("home/search_keyword");
  return model;
 }
 
//Search page for starting searches by group
@RequestMapping(value= {"/home/searchgroups"}, method=RequestMethod.GET)
public ModelAndView searchGroups() {
ModelAndView model = new ModelAndView();
model.setViewName("home/search");
return model;
}
 
 // Displaying list of products by group
/*
 @RequestMapping(value= {"/home/listproducts/{productGroupId}"}, method=RequestMethod.GET)
 public ModelAndView listProducts(@PathVariable(name = "productGroupId") Long productGroupId) {

	 ProductGroup productGroup = productGroupRepository.findById(productGroupId).get();
	 List<Product> productList = productServiceImpl.findProductsByGroup(productGroup);
	 List<ProductQuantity> productQuantitiyList = new ArrayList<ProductQuantity>();
	 productQuantitiyList = getProductQuantityList(productList);
	 
	 ModelAndView model = new ModelAndView();
	 model.addObject("productQuantityList", productQuantitiyList);
	 model.addObject("message", productGroup.getName());
	 model.addObject("productList", productList);
	 model.setViewName("home/list_products");
	 return model;
 	}
 */

// SEARCH PRODUCTS BY GROUP
@RequestMapping(value= {"/home/listproducts/{productGroupId}"}, method=RequestMethod.GET)
public String listProductsByGroup(@PathVariable(name = "productGroupId") Long productGroupId) {

	 ProductGroup productGroup = productGroupRepository.findById(productGroupId).get();
	 productList = productServiceImpl.findProductsByGroup(productGroup);
	 // productQuantityList = getProductQuantityList(productList);
	 return "redirect:/user/displayproducts";
	}
 
 // DISPLAY PRODUCTS
 @RequestMapping(value= {"/user/displayproducts"}, method=RequestMethod.GET)
 public ModelAndView displayKeywordPosts(HttpServletRequest request) {
	 ModelAndView model = new ModelAndView();
	 if(productList.isEmpty()) {
		   model.setViewName("home/home_search");
		   model.addObject("err", "Nisu pronađeni proizvodi. Pokušajte ponovo.");
	 } else {
	  	   
	       int page = 0; //default page number is 0
	       int size = 10; //default page size is 10
	       
	       if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
	           page = Integer.parseInt(request.getParameter("page")) - 1;
	       }

	       if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
	           size = Integer.parseInt(request.getParameter("size"));
	       }

	       	Page <Product> productList = null;
	   		productList = findPaginated(PageRequest.of(page, size));
	   
	   		productQuantityList = getProductQuantityList((List<Product>) productList);
	   
		   	model.addObject("message", "Rezultat pretrage auto dijelova");
		   	model.addObject("productList", productList);
		   	model.addObject("productList", productQuantityList);
			 model.setViewName("home/list_products");
	 	}
  	   return model;
 	}
 
 // CREATING PAGEABLE LIST
 public Page<Product> findPaginated(Pageable pageable) {
     int pageSize = pageable.getPageSize();
     int currentPage = pageable.getPageNumber();
     int startItem = currentPage * pageSize;
     List<Product> list;

     if (productList.size() < startItem) {
         list = Collections.emptyList();
     } else {
         int toIndex = Math.min(startItem + pageSize, productList.size());
         list = productList.subList(startItem, toIndex);
     }

     Page<Product> productsPage
       = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), productList.size());

     return productsPage;
 }

 // Displaying replacement products for selected product - auto part
@RequestMapping(value= {"/home/listreplaceproducts/{id}"}, method=RequestMethod.GET)
 public ModelAndView listReplaceProducts(@PathVariable(name = "id") Long id) {
	 Product product = productRepository.findById(id).get();
	 List <Product> productList = product.getProducts();
	 String message2 = null;
	 if(productList.size() == 0) {
		 message2 = "Nema zamjenskog dijela";
	 } else {
		 message2 = "Zamjenski dijelovi za šifru " + product.getSifra();
	 }
	 List <ProductQuantity> productQuantityList = new ArrayList<ProductQuantity>();
	 productQuantityList = getProductQuantityList(productList);
  ModelAndView model = new ModelAndView();
  model.addObject("message", product.getProductGroup().getName());
  model.addObject("message2", message2);
  model.addObject("productQuantityList", productQuantityList);
  model.addObject("productList", productList);
  model.setViewName("home/list_products");
  return model;
 }
 
// Displaying selected product profile by ID
 @RequestMapping(value= {"/home/productprofile/{id}"}, method=RequestMethod.GET)
 public ModelAndView productProfile(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  String msg = null;
  
  if(product == null) {
	  msg = "Nije pronađen proizvod sa zadanim ID brojem";
	  model.addObject("msg", msg);
  } else {
	  List <Product> replaceProducts = product.getProducts();
	  User user = getCurrentUser();
	  ProductQuantity productQuantity;
	  try {
		  productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
	  } catch(Exception e) {
		  UserProduct userProduct = new UserProduct(user.getId(), product.getId());
		  productQuantity = new ProductQuantity(userProduct, 0);
	  }
	  
	  model.addObject("replaceProducts", replaceProducts);
	  model.addObject("msg", "Pregled profila traženog proizvoda!");
	  model.addObject("productQuantity", productQuantity);
	  model.addObject("product", product);
	  model.setViewName("home/product_profile");
  	}
  return model;
 }
 
 // SEARCH BY SIFRA Displaying product profile after search by sifra
 @RequestMapping(value= {"/home/productprofile"}, method=RequestMethod.POST)
 public ModelAndView productProfile(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findBySifra(sampleInputs.getSifra());

  if(product == null) {
   model.setViewName("home/search_keyword");
   model.addObject("err", "Nije pronađen artikl sa unesenom šifrom. Pokušajte ponovo.");
  } else {
	  List <Product> replaceProducts = product.getProducts();
	  User user = getCurrentUser();
	  ProductQuantity productQuantity;
	  try {
		  productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
	  } catch(Exception e) {
		  UserProduct userProduct = new UserProduct(user.getId(), product.getId());
		  productQuantity = new ProductQuantity(userProduct, 0);
	  }

	  model.addObject("replaceProducts", replaceProducts);
	  model.addObject("msg", "Pregled profila traženog proizvoda!");
	  model.addObject("product", product);
	  model.addObject("productQuantity", productQuantity);
	  model.setViewName("home/product_profile");
  	}
  return model;
 }
 
 // After back button is clicked return back beginning of creating a product
 @RequestMapping(value= {"home/productprofile"}, method=RequestMethod.GET)
 public String backToSearchProduct() {
	 return "redirect:/home/search";
 }
 
 // SEARCH PARTS BY KEYWORD
 @RequestMapping(value= {"/home/keywordsearch"}, method=RequestMethod.POST)
 public ModelAndView keywordSearch(@Valid SampleInputs inputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  	String keyWord = inputs.getName();
  	productList = productRepository.findByNameContaining(keyWord);
  	if(productList.isEmpty() || keyWord.equals("")) {
  		model.setViewName("home/search_keyword");
  		model.addObject("err", "Nisu pronađeni artikli koji sadrže unesenu ključnu riječ. Pokušajte ponovo.");
  	} else {
  		 productQuantityList = new ArrayList<ProductQuantity>();
  		 productQuantityList = getProductQuantityList(productList);
  		 model.addObject("message", "Lista artikala koji sadrže ključnu riječ: " + keyWord);
  		 model.addObject("productList", productList);
  		 model.addObject("productQuantityList", productQuantityList);
  		 model.setViewName("home/list_products");
  	}
  return model;
 }
 
 // After back button is clicked return back beginning of searching products by sifra and keyword
 @RequestMapping(value= {"home/keywordsearch"}, method=RequestMethod.GET)
 public String backToSearch() {
	 return "redirect:/home/search";
 }
 
 // Main page for starting different searches for auto parts products
 @RequestMapping(value= {"/home/homesearch"}, method=RequestMethod.GET)
 public ModelAndView homeSearch() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/home_search");
  return model;
 }
 
 // Starting search by auto brand and model - first selecting the brand
 @RequestMapping(value= {"/home/modelsearch"}, method=RequestMethod.GET)
 public ModelAndView modelSearch() {
  ModelAndView model = new ModelAndView();
  List<Brand> brands = (List<Brand>) brandRepository.findAll();
  model.addObject("brands", brands);
  CarModel carModel = new CarModel();
  model.addObject("carModel", carModel);
  model.setViewName("home/select_brand");
  return model;
 }
 
 // Redirecting user if browser back button is clicked in order to start from the beginning (brand)
 @RequestMapping(value= {"/home/modelsearch2"}, method=RequestMethod.GET)
 public String redirectToBrandSearch(HttpServletRequest request) {
	 return "redirect:/home/modelsearch";
 }
 
 // Displaying car models of certain auto brand to be selected by user
 @RequestMapping(value= {"/home/modelsearch2"}, method=RequestMethod.POST)
 public ModelAndView modelSearch2(@Valid CarModel carModel, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  List<CarModel> carModels = carModelRepository.findByBrand(carModel.getBrand());
  model.addObject("carModel", carModel);
  model.addObject("carModels", carModels);
  model.setViewName("home/select_model");
  return model;
 }
 
 // Redirecting user to the beginning to select the brand first if browser back button is used
 @RequestMapping(value= {"/home/modelsearch3"}, method=RequestMethod.GET)
 public String redirectBackToBrandSearch(HttpServletRequest request) {
	 return "redirect:/home/modelsearch";
 }
 
 // Display product groups and electing product group after brand and model were selected 
 @RequestMapping(value= {"/home/modelsearch3"}, method=RequestMethod.POST)
 public ModelAndView modelSearch3(@Valid CarModel carModel, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  CarModel selectedCarModel = carModelRepository.findByName(carModel.getName());
  SampleInputs inputs = new SampleInputs();
  inputs.setBrandName(selectedCarModel.getBrand().getName());
  inputs.setModelName(selectedCarModel.getName());
  model.addObject("inputs", inputs);
  model.setViewName("home/select_group");
  return model;
 }
 
 // Conducting search based on selected parameters of brand, model and group
 @RequestMapping(value= {"/home/modelsearch4"}, method=RequestMethod.POST)
 public ModelAndView modelSearch4(@Valid SampleInputs inputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  
  // System.out.println("GROUP NAME " + inputs.getId());
  ProductGroup group = productGroupRepository.findById(inputs.getId()).get();
 
  // System.out.println("BRAND NAME " + inputs.getBrandName());
  String carBrand = inputs.getBrandName();
  
  // System.out.println("MODEL NAME " + inputs.getModelName());
  String carModel = inputs.getModelName();
  
  String pattern = "%" + carBrand + "%" + carModel + "%";
  	productList = productRepository.findByDescriptionLikeAndProductGroup(pattern, group);
  	// System.out.println("id group ID GRupe: " + group.getId());
  	if(checkOils(group.getId())) {
  		productList = productRepository.findByProductGroup(group);
  	}
	 productQuantityList = new ArrayList<ProductQuantity>();
	 productQuantityList = getProductQuantityList(productList);
  model.addObject("message", group.getName());
  model.addObject("productList", productList);
  model.addObject("productQuantityList", productQuantityList);
  model.setViewName("home/list_products");
  return model;
 }
 
 // If browser back button is clicked display last list of product from the last search results
 @RequestMapping(value= {"/home/modelsearch4"}, method=RequestMethod.GET)
 public ModelAndView redirectBackToProductsList() {
	  ModelAndView model = new ModelAndView();
	  model.addObject("message", productList.get(0).getProductGroup().getName());
	  model.addObject("productList", productList);
	  model.addObject("productQuantityList", productQuantityList);
	  model.setViewName("home/list_products");
	  return model;
 }
 
 // Checking if the selected product group are liquids or oils. If yes all are returned in line 247
 private boolean checkOils(Long id) {
	if(id == 1 || id == 41 || id == 51 || id == 61 || id == 71 || id == 81 || id == 91 || id == 101 || id == 191 || id == 201 || id == 211 || id == 221 || id == 231 || id == 241 || id == 251 || id == 261 || id == 271 || id == 281 || id == 291 || id == 301 || id == 311 || id == 321 || id == 331 || id == 341 || id == 351 || id == 361 || id == 371 || id == 381 || id == 391 || id == 401 || id == 411 || id == 421 || id == 431 || id == 431 || id == 351 || id == 461 || id == 471) {
		return true;
	} else {
		return false;
	}
}

 // Getting current user for different purposes
private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
 }
 
 // Getting current quantity for current user for each product in the list
 private List<ProductQuantity> getProductQuantityList(List<Product> productList) {
	 User user = getCurrentUser();
	 List<ProductQuantity> productQuantitiyList = new ArrayList<ProductQuantity>();
	 for(Product product: productList) {
		 ProductQuantity productQuantity;
		 try {
			 productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
		 } catch(Exception e) {
			 productQuantity = new ProductQuantity(new UserProduct(user.getId(), product.getId()), 0);
			 // productQuantityRepository.save(productQuantity);
		 }
		 productQuantitiyList.add(productQuantity);
	 }
	return productQuantitiyList;
}
}