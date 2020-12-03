package com.avlija.parts.controller;

import java.util.ArrayList;

import java.util.List;

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
import com.avlija.parts.model.Brand;
import com.avlija.parts.model.CarModel;
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
 

 @RequestMapping(value= {"/home/search"}, method=RequestMethod.GET)
 public ModelAndView search() {
  ModelAndView model = new ModelAndView();
  model.addObject("sampleInputs", new SampleInputs());
  model.setViewName("home/search");
  return model;
 }
 
 /*
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
 
 @RequestMapping(value= {"/home/motorfilteri"}, method=RequestMethod.GET)
 public ModelAndView motorFilteri() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_motor_filteri");
  return model;
 }
 
 @RequestMapping(value= {"/home/filteri"}, method=RequestMethod.GET)
 public ModelAndView filteri() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_filteri");
  return model;
 }
 
 @RequestMapping(value= {"/home/filterulja"}, method=RequestMethod.GET)
 public ModelAndView filterUlja() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_filteri_ulja");
  return model;
 }
 
 @RequestMapping(value= {"/home/filterzraka"}, method=RequestMethod.GET)
 public ModelAndView filterZraka() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_filteri_zraka");
  return model;
 }
 
 @RequestMapping(value= {"/home/filtergoriva"}, method=RequestMethod.GET)
 public ModelAndView filterGoriva() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/search_filteri_goriva");
  return model;
 }
 */
 
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
 
 @RequestMapping(value= {"/home/productprofile"}, method=RequestMethod.POST)
 public ModelAndView productProfile(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findBySifra(sampleInputs.getSifra());

  if(product == null) {
   model.setViewName("home/search");
   model.addObject("msg", "Nije pronađen artikl sa unesenom šifrom. Pokušajte ponovo.");
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
 
 @RequestMapping(value= {"/home/homesearch"}, method=RequestMethod.GET)
 public ModelAndView homeSearch() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/home_search");
  return model;
 }
 
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
 
 @RequestMapping(value= {"/home/modelsearch2"}, method=RequestMethod.POST)
 public ModelAndView modelSearch2(@Valid CarModel carModel, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  List<CarModel> carModels = carModelRepository.findByBrand(carModel.getBrand());
  model.addObject("carModel", carModel);
  model.addObject("carModels", carModels);
  model.setViewName("home/select_model");
  return model;
 }
 
 @RequestMapping(value= {"/home/modelsearch3"}, method=RequestMethod.POST)
 public ModelAndView modelSearch3(@Valid CarModel carModel, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  CarModel selectedCarModel = carModelRepository.findByName(carModel.getName());
  System.out.println("SELECTED CAR Model " + selectedCarModel + carModel.getName());
  List<ProductGroup> productGroups = productGroupRepository.findAll();
  productGroups.remove(0);
  SampleInputs inputs = new SampleInputs();
  inputs.setBrandName(selectedCarModel.getBrand().getName());
  inputs.setModelName(selectedCarModel.getName());
  model.addObject("inputs", inputs);
  model.addObject("productGroups", productGroups);
  model.setViewName("home/select_group");
  return model;
 }
 
 @RequestMapping(value= {"/home/modelsearch4"}, method=RequestMethod.POST)
 public ModelAndView modelSearch4(@Valid SampleInputs inputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  System.out.println("GROUP NAME " + inputs.getId());
  ProductGroup group = productGroupRepository.findById(inputs.getId()).get();
  
  System.out.println("BRAND NAME " + inputs.getBrandName());
  String carBrand = inputs.getBrandName();
  
  System.out.println("MODEL NAME " + inputs.getModelName());
  String carModel = inputs.getModelName();
  
  String pattern = "%" + carBrand + "%" + carModel + "%";
  	List<Product> productList = productRepository.findByDescriptionLikeAndProductGroup(pattern, group);
  	System.out.println("id grupe ID GRupe: " + group.getId());
  	if(checkOils(group.getId())) {
  		productList = productRepository.findByProductGroup(group);
  	}
	 List<ProductQuantity> productQuantitiyList = new ArrayList<ProductQuantity>();
	 productQuantitiyList = getProductQuantityList(productList);
  model.addObject("message", group.getName());
  model.addObject("productList", productList);
  model.addObject("productQuantityList", productQuantitiyList);
  model.setViewName("home/list_products");
  return model;
 }
 
 private boolean checkOils(Long id) {
	if(id == 1 || id == 41 || id == 51 || id == 61 || id == 71 || id == 81 || id == 91 || id == 101 || id == 191 || id == 201 || id == 211 || id == 221 || id == 231 || id == 241 || id == 251 || id == 261 || id == 271 || id == 281 || id == 291 || id == 301 || id == 311 || id == 321 || id == 331 || id == 341 || id == 351 || id == 361 || id == 371 || id == 381 || id == 391 || id == 401 || id == 411 || id == 421 || id == 431 || id == 431 || id == 351 || id == 461 || id == 471) {
		return true;
	} else {
		return false;
	}
}

private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
 }
 
 
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