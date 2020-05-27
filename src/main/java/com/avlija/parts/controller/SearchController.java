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
import com.avlija.parts.model.Brand;
import com.avlija.parts.model.CarModel;
import com.avlija.parts.model.Product;
import com.avlija.parts.model.ProductGroup;
import com.avlija.parts.model.ProductMaker;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.BrandRepository;
import com.avlija.parts.repository.CarModelRepository;
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
 
 @Autowired
 private BrandRepository brandRepository;
 
 @Autowired
 private CarModelRepository carModelRepository;
 
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
   model.setViewName("home/search");
   model.addObject("msg", "Nije pronađen artikl sa unesenom šifrom. Pokušajte ponovo.");
  } else {
	  Set<Product> replaceProducts = product.getProducts();
	  model.addObject("replaceProducts", replaceProducts);
	  model.addObject("msg", "Pregled profila traženog proizvoda!");
	  model.addObject("product", product);
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
  model.addObject("carModel", new CarModel());
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
  
  System.out.println("GROUP NAME " + inputs.getGroupName());
  ProductGroup group = productGroupRepository.findByName(inputs.getGroupName());
  
  System.out.println("BRAND NAME " + inputs.getBrandName());
  String carBrand = inputs.getBrandName();
  
  System.out.println("MODEL NAME " + inputs.getModelName());
  String carModel = inputs.getModelName();
  
  String pattern = "%" + carBrand + "%" + carModel + "%";
  List<Product> productList = productRepository.findByDescriptionLike(pattern);
  model.addObject("message", inputs.getGroupName());
  model.addObject("productList", productList);
  model.setViewName("home/list_products");
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