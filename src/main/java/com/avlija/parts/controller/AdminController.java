package com.avlija.parts.controller;

import java.util.Date;
import java.util.HashSet;
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
import com.avlija.parts.model.ProductQuantity;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;
import com.avlija.parts.model.UserProduct;
import com.avlija.parts.repository.BrandRepository;
import com.avlija.parts.repository.CarModelRepository;
import com.avlija.parts.repository.ProductGroupRepository;
import com.avlija.parts.repository.ProductMakerRepository;
import com.avlija.parts.repository.ProductQuantityRepository;
import com.avlija.parts.repository.ProductRepository;
import com.avlija.parts.repository.TransactionRepository;
import com.avlija.parts.service.ProductServiceImpl;
import com.avlija.parts.service.UserService;

@Controller
public class AdminController {

 @Autowired
 private CarModelRepository carModelRepository;
 
 @Autowired
 private ProductGroupRepository productGroupRepository;
 
 @Autowired
 private ProductMakerRepository productMakerRepository;
 
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
 ProductQuantityRepository productQuantityRepository;
 

 @RequestMapping(value= {"admin/creategroup"}, method=RequestMethod.GET)
 public ModelAndView createGroup() {
  ModelAndView model = new ModelAndView();
  ProductGroup productGroup = new ProductGroup();
  model.addObject("productGroup", productGroup);
  model.setViewName("admin/create_group");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/creategroup"}, method=RequestMethod.POST)
 public ModelAndView createGroup(@Valid ProductGroup productGroup, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  ProductGroup productGroupExists = productGroupRepository.findByName(productGroup.getName());
  
  if(productGroupExists != null) {
   bindingResult.rejectValue("name", "error.productGroup", "Ova kategorija već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("admin/create_group");
  } else {
	  productGroupRepository.save(productGroup);
   model.addObject("msg", "Nova kategorija je uspješno kreirana!");
   model.addObject("productGroup", new ProductGroup());
   model.setViewName("admin/create_group");
  }
  return model;
 }
 
 @RequestMapping(value= {"admin/createmaker"}, method=RequestMethod.GET)
 public ModelAndView createMaker() {
  ModelAndView model = new ModelAndView();
  ProductMaker productMaker = new ProductMaker();
  model.addObject("productMaker", productMaker);
  model.setViewName("admin/create_maker");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createmaker"}, method=RequestMethod.POST)
 public ModelAndView createMaker(@Valid ProductMaker productMaker, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  ProductMaker productMakerExists = productMakerRepository.findByName(productMaker.getName());
  
  if(productMakerExists != null) {
   bindingResult.rejectValue("name", "error.productMaker", "Ovaj proizvođač već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("admin/create_maker");
  } else {
	  productMakerRepository.save(productMaker);
	  model.addObject("msg", "Novi proizvođać je uspješno kreiran!");
	  model.addObject("productMaker", new ProductMaker());
	  model.setViewName("admin/create_maker");
  	}
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct"}, method=RequestMethod.GET)
 public ModelAndView createProduct() {
  ModelAndView model = new ModelAndView();
  Product product = new Product();
  List<ProductGroup> productGroupList = productGroupRepository.findAll();
  List<ProductMaker> productMakerList = productMakerRepository.findAll();
  model.addObject("product", product);
  model.addObject("productMakerList", productMakerList);
  model.addObject("productGroupList", productGroupList);
  model.setViewName("admin/create_product");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct1"}, method=RequestMethod.POST)
 public ModelAndView createProduct(@Valid Product product, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product productExists = productRepository.findBySifra(product.getSifra());
  
  if(productExists != null) {
   bindingResult.rejectValue("sifra", "error.product", "Ova šifra već postoji!");
  }
  if(bindingResult.hasErrors()) {
	  List<ProductGroup> productGroupList = productGroupRepository.findAll();
	  List<ProductMaker> productMakerList = productMakerRepository.findAll();
	  model.addObject("product", product);
	  model.addObject("productMakerList", productMakerList);
	  model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  } else {

	  ProductGroup productGroup = product.getProductGroup();
	  List<Product> productList = productServiceImpl.findProductsByGroup(productGroup);
	  
	  productRepository.save(product);
	  Long productId = product.getId();
	  Product savedProduct = productRepository.findById(productId).get();
	  
	  model.addObject("productList", productList);
	  model.addObject("product", savedProduct);
	  model.setViewName("admin/create_product2");
  }
  return model;
 }
 
 @RequestMapping(value= {"admin/createproduct2"}, method=RequestMethod.POST)
 public ModelAndView createProduct2(@Valid Product product, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Set<Product> replaceProducts = new HashSet<Product>();
  	if(product.getProducts() == null) {
  		product.setProducts(replaceProducts);
  	} else {
  		replaceProducts = product.getProducts();
  	}
  	   Product savedProduct = productRepository.findById(product.getId()).get();
  	   
  	   savedProduct.setProducts(replaceProducts);
  	   productRepository.save(savedProduct);
  	   model.addObject("msg", "Novi auto dio je uspješno kreiran!");
   
   List<ProductGroup> productGroupList = productGroupRepository.findAll();
   List<ProductMaker> productMakerList = productMakerRepository.findAll();
   model.addObject("product", new Product());
   model.addObject("productMakerList", productMakerList);
   model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  return model;
 }
 
 
 @RequestMapping(value= {"admin/editproduct/{id}"}, method=RequestMethod.GET)
 public ModelAndView editProduct(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  model.addObject("product", product);
  model.setViewName("admin/edit_product");
  return model;
 }
 
 @RequestMapping(value= {"admin/editproduct1"}, method=RequestMethod.POST)
 public ModelAndView editProduct(@Valid Product product, ProductGroup productGroup) {
  ModelAndView model = new ModelAndView();
  
  	Product savedProduct = productRepository.findById(product.getId()).get();
  		product.setProductGroup(savedProduct.getProductGroup());
  		product.setProductMaker(savedProduct.getProductMaker());
  		product.setProducts(savedProduct.getProducts());

	  List<Product> productList = productServiceImpl.findProductsByGroup(savedProduct.getProductGroup());
	  productRepository.save(product);
	  
	  model.addObject("msg", "Dopuna zamjenskih dijelova za šifru - " + product.getSifra() +
			  			" - iz kategorije - " + product.getProductGroup().getName());
	  model.addObject("productList", productList);
	  model.addObject("product", product);
	  model.setViewName("admin/edit_product2");
 
  return model;
 }
 
 @RequestMapping(value= {"admin/editproduct2"}, method=RequestMethod.POST)
 public ModelAndView editProduct2(@Valid Product product) {
  ModelAndView model = new ModelAndView();
  Set<Product> replaceProducts = new HashSet<Product>();
  	if(product.getProducts() == null) {
  		product.setProducts(replaceProducts);
  	} else {
  		replaceProducts = product.getProducts();
  	}
  	   Product savedProduct = productRepository.findById(product.getId()).get();
  	   
  	   savedProduct.setProducts(replaceProducts);
  	   productRepository.save(savedProduct);
  	   model.addObject("msg", "Auto dio uspješno izmjenjen!");

  	   model.addObject("replaceProducts", replaceProducts);
  	   model.addObject("product", savedProduct);
  	   model.setViewName("home/product_profile");
  	   return model;
 }
 
 @RequestMapping(value= {"/admin/addremove/{id}"}, method=RequestMethod.GET)
 public ModelAndView productProfile(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(id).get();
  User user = getCurrentUser();
  String msg = null;
  
  if(product == null) {
	  msg = "Nije pronađen proizvod sa zadanim ID brojem";
	  model.addObject("msg", msg);
  		} else {
  			SampleInputs sampleInputs = new SampleInputs();
  			sampleInputs.setId(id);
			ProductQuantity productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
  			model.addObject("sampleInputs", sampleInputs);
  			model.addObject("productQuantity", productQuantity);
  			model.addObject("msg", "Transakcija proizvoda na stanju!");
  			model.setViewName("admin/product_add_remove");
  		}
	model.addObject("product", product);
  return model;
 }
 
 @RequestMapping(value= {"/admin/add"}, method=RequestMethod.POST)
 public ModelAndView addProduct(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(sampleInputs.getId()).get();
  	User user = getCurrentUser();
	  ProductQuantity oldProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();

  if(sampleInputs.getQuantity() == null) {
		SampleInputs inputs = new SampleInputs();
		inputs.setId(product.getId());
		model.addObject("sampleInputs", inputs);
		model.addObject("product", product);
		  model.addObject("productQuantity", oldProductQuantity);
		model.addObject("msg", "Molim vas unesite količinu artikla u odgovarajuće polje!");
		model.setViewName("admin/product_add_remove");
  	} else {
  	  		int oldQuantity;
  	  try {
  	  		oldQuantity = oldProductQuantity.getQuantity();
  	  } catch(Exception e) {
  	  	  		UserProduct userProduct = new UserProduct(user.getId(), product.getId());
  	  			ProductQuantity productQuantity = new ProductQuantity(userProduct, 0);
  	  			productQuantityRepository.save(productQuantity);
  	  			oldQuantity = 0;
  	  			}
	  	int newQuantiy = oldQuantity + sampleInputs.getQuantity();
	  	ProductQuantity newProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();
	  	newProductQuantity.setQuantity(newQuantiy);
	  	productQuantityRepository.save(newProductQuantity);
	  	
	  	double total = sampleInputs.getQuantity() * product.getPrice();
	  	String transType = "ULAZ";
	  	Transaction transaction = new Transaction(new Date(), sampleInputs.getQuantity(), total, transType, product, user);
	  	transactionRepository.save(transaction);
	  	
		  Set<Product> replaceProducts = product.getProducts();
		  newProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), product.getId())).get();

		  model.addObject("replaceProducts", replaceProducts);
		  model.addObject("msg", "Pregled profila nakon dodavanja ili oduzimanja određene količine artikla!");
		  model.addObject("product", product);
		  model.addObject("productQuantity", newProductQuantity);
		  model.setViewName("home/product_profile");
  	}
  return model;
 }
 
 /*
 @RequestMapping(value= {"/admin/remove"}, method=RequestMethod.POST)
 public ModelAndView removeProduct(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Product product = productRepository.findById(sampleInputs.getId()).get();
  
  if(sampleInputs.getQuantity() == null) {
		SampleInputs inputs = new SampleInputs();
		inputs.setId(product.getId());
		model.addObject("sampleInputs", inputs);
		model.addObject("msg", "Molim vas unesite količinu artikla u odgovarajuće polje!");
		model.setViewName("admin/product_add_remove");
  	}  else if(product.getQuantity() <= 0 || product.getQuantity() - sampleInputs.getQuantity() < 0){
  			Set<Product> replaceProducts = product.getProducts();
  			model.addObject("replaceProducts", replaceProducts);
  			model.addObject("msg", "Nema proizvoda na stanju ili količina na stanju nije dovoljna za transakciju!");
  			model.setViewName("home/product_profile");
  		} else {
  			int oldQuantity = product.getQuantity();
  			int newQuantiy = oldQuantity - sampleInputs.getQuantity();
  			product.setQuantity(newQuantiy);
  			productRepository.save(product);
  			
  		  	double total = sampleInputs.getQuantity() * product.getPrice();
  		  	String transType = "IZLAZ";
  		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  		    User user = userService.findUserByEmail(auth.getName());
  		  	Transaction transaction = new Transaction(new Date(), sampleInputs.getQuantity(), total, transType, product, user);
  		  	transactionRepository.save(transaction);
  	
  			Set<Product> replaceProducts = product.getProducts();
  			model.addObject("replaceProducts", replaceProducts);
  			model.addObject("msg", "Pregled profila nakon dodavanja ili oduzimanja određene količine artikla!");
  			model.setViewName("home/product_profile");
  		}
	model.addObject("product", product);
  	return model;
 }
 */
 
 @RequestMapping(value= {"admin/createbrand"}, method=RequestMethod.GET)
 public ModelAndView createBrand() {
  ModelAndView model = new ModelAndView();
  Brand brand = new Brand();
  model.addObject("brand", brand);
  model.setViewName("admin/create_brand");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createbrand"}, method=RequestMethod.POST)
 public ModelAndView createBrand(@Valid Brand brand, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Brand brandExists = brandRepository.findByName(brand.getName());
  
  if(brandExists != null) {
   bindingResult.rejectValue("name", "error.brand", "Ova marka automobila već postoji!");
  }
  if(bindingResult.hasErrors()) {
   model.setViewName("admin/create_brand");
  } else {
	  brandRepository.save(brand);
   model.addObject("msg", "Nova marka automobila je uspješno kreirana!");
   model.addObject("brand", new Brand());
   model.setViewName("admin/create_brand");
  }
  return model;
 }
 
 @RequestMapping(value= {"admin/createcarmodel"}, method=RequestMethod.GET)
 public ModelAndView createCarModel() {
  ModelAndView model = new ModelAndView();
  CarModel carModel = new CarModel();
  List<Brand> brands = (List<Brand>) brandRepository.findAll();
  model.addObject("carModel", carModel);
  model.addObject("brands", brands);
  model.setViewName("admin/create_car_model");
  
  return model;
 }
 
 @RequestMapping(value= {"admin/createcarmodel"}, method=RequestMethod.POST)
 public ModelAndView createCarModel(@Valid CarModel carModel, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  CarModel carModelExists = carModelRepository.findByName(carModel.getName());
  
  if(carModelExists != null) {
   bindingResult.rejectValue("name", "error.carModel", "Ova model automobila već postoji!");
  }
  if(bindingResult.hasErrors()) {
	  System.out.println("There is error");
  } else {
	  carModelRepository.save(carModel);
	   model.addObject("msg", "Novi model automobila je uspješno kreiran!");
  }
  
  List<Brand> brands = (List<Brand>) brandRepository.findAll();
  model.addObject("carModel", new CarModel());
  model.addObject("brands", brands);
  model.setViewName("admin/create_car_model");
  return model;
 }
 
 private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
}

 /*
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