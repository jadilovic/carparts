package com.avlija.parts.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.avlija.parts.repository.UserRepository;
import com.avlija.parts.service.ProductMakerNameSorter;
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
 private ProductQuantityRepository productQuantityRepository;
 
 @Autowired
 private UserRepository userRepository;
 
 private static Product savedProduct;
 
// Creating product group or category
 @RequestMapping(value= {"admin/creategroup"}, method=RequestMethod.GET)
 public ModelAndView createGroup() {
  ModelAndView model = new ModelAndView();
  ProductGroup productGroup = new ProductGroup();
  model.addObject("productGroup", productGroup);
  model.setViewName("admin/create_group");
  
  return model;
 }
 
 // CREATING PRODUCT GROUP AND DOWNLOADING AND SAVING JSON FILE FOR DISPLAY OF GROUPS IN JAVASCRIPT
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

 // Creating product maker / producer name
@RequestMapping(value= {"admin/createmaker"}, method=RequestMethod.GET)
 public ModelAndView createMaker() {
  ModelAndView model = new ModelAndView();
  ProductMaker productMaker = new ProductMaker();
  model.addObject("productMaker", productMaker);
  model.setViewName("admin/create_maker");
  
  return model;
 }
 
// Creating product maker / producer name
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
 
 // Creating product - auto part in the database
 @RequestMapping(value= {"admin/createproduct"}, method=RequestMethod.GET)
 public ModelAndView createProduct() {
  ModelAndView model = new ModelAndView();
  Product product = new Product();
  List<ProductGroup> productGroupList = productGroupRepository.findAll();
  List<ProductMaker> productMakerList = productMakerRepository.findAll();
  productMakerList.sort(new ProductMakerNameSorter().reversed());
  model.addObject("product", product);
  model.addObject("productMakerList", productMakerList);
  model.addObject("productGroupList", productGroupList);
  model.setViewName("admin/create_product");
  
  return model;
 }
 
 // Creating product - auto part in the database - entering data
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
	  productMakerList.sort(new ProductMakerNameSorter().reversed());
	  model.addObject("product", product);
	  model.addObject("productMakerList", productMakerList);
	  model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  } else {

	  ProductGroup productGroup = product.getProductGroup();
	  List<Product> productList = productServiceImpl.findProductsByGroup(productGroup);
	  
	  productRepository.save(product);
	  Long productId = product.getId();
	  savedProduct = productRepository.findById(productId).get();
 	  // ADDING NEW PRODUCT TO EXISTING USERS
	  List<User> usersList = userRepository.findAll();
 	  newProductAddedToExistingUsers(savedProduct, usersList);
	  
	  model.addObject("productList", productList);
	  model.addObject("product", savedProduct);
	  model.setViewName("admin/create_product2");
  }
  return model;
 }
 
 // After back button is clicked return back beginning of creating a product
 @RequestMapping(value= {"admin/createproduct1"}, method=RequestMethod.GET)
 public String backToCreateProductBeginning() {
	 return "redirect:/admin/createproduct";
 }
 
 // Creating product - auto part in the database - adding replacement parts and finalizing the product in the database
 @RequestMapping(value= {"admin/createproduct2"}, method=RequestMethod.POST)
 public ModelAndView createProduct2(@Valid Product product, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  List <Product> replaceProducts = new ArrayList<Product>();
  	if(product.getProducts() == null) {
  		product.setProducts(replaceProducts);
  	} else {
  		replaceProducts = product.getProducts();
  	}
  	
  	   savedProduct.setProducts(replaceProducts);
  	   productRepository.save(savedProduct);
  	   model.addObject("msg", "Novi auto dio je uspješno kreiran!");
   
   List<ProductGroup> productGroupList = productGroupRepository.findAll();
   List<ProductMaker> productMakerList = productMakerRepository.findAll();
   productMakerList.sort(new ProductMakerNameSorter().reversed());

   model.addObject("product", new Product());
   model.addObject("productMakerList", productMakerList);
   model.addObject("productGroupList", productGroupList);
   model.setViewName("admin/create_product");
  return model;
 }
 
 // After back button is clicked return back beginning of creating a product
 @RequestMapping(value= {"admin/createproduct2"}, method=RequestMethod.GET)
 public String backToCreateProduct() {
	 return "redirect:/admin/createproduct";
 }
 
// Editing database product - auto part
@RequestMapping(value= {"admin/editproduct/{id}"}, method=RequestMethod.GET)
 public ModelAndView editProduct(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  savedProduct = productRepository.findById(id).get();
  model.addObject("product", savedProduct);
  model.setViewName("admin/edit_product");
  return model;
 }
 
//Editing database product - auto part - adding and changing data
 @RequestMapping(value= {"admin/editproduct1"}, method=RequestMethod.POST)
 public ModelAndView editProduct(@Valid Product product, ProductGroup productGroup) {
  ModelAndView model = new ModelAndView();
  
  	savedProduct = productRepository.findById(product.getId()).get();
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
 
 // After back button is clicked return back beginning of edit product
 @RequestMapping(value= {"admin/editproduct1"}, method=RequestMethod.GET)
 public String backToProductEdit() {
	 Long productId = savedProduct.getId();
	 return "redirect:/admin/editproduct/" + productId;
 }
 
// Editing database product - auto part - adding or changing replacement products
 @RequestMapping(value= {"admin/editproduct2"}, method=RequestMethod.POST)
 public ModelAndView editProduct2(@Valid Product product) {
  ModelAndView model = new ModelAndView();
  List<Product> replaceProducts = new ArrayList<Product>();
  	if(product.getProducts() == null) {
  		product.setProducts(replaceProducts);
  	} else {
  		replaceProducts = product.getProducts();
  	}
  	   savedProduct = productRepository.findById(product.getId()).get();
  	   
  	   savedProduct.setProducts(replaceProducts);
  	   productRepository.save(savedProduct);
  	   model.addObject("msg", "Auto dio uspješno izmjenjen!");
  	   
  	   User user = getCurrentUser();
		ProductQuantity productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();

  	   model.addObject("replaceProducts", replaceProducts);
  	   model.addObject("product", savedProduct);
  	   model.addObject("productQuantity", productQuantity);
  	   model.setViewName("home/product_profile");
  	   return model;
 }
 
 // After back button is clicked return back beginning of edit product
 @RequestMapping(value= {"admin/editproduct2"}, method=RequestMethod.GET)
 public String backToProductEditPage() {
	 Long productId = savedProduct.getId();
	 return "redirect:/admin/editproduct/" + productId;
 }
 
 // Starting page for adding or subtracting product quantity in the database
 @RequestMapping(value= {"/user/addremove/{id}"}, method=RequestMethod.GET)
 public ModelAndView productProfile(@PathVariable(name = "id") Long id) {
  ModelAndView model = new ModelAndView();
  savedProduct = productRepository.findById(id).get();
  User user = getCurrentUser();
  String msg = null;
  
  if(savedProduct == null) {
	  msg = "Nije pronađen proizvod sa zadanim ID brojem";
	  model.addObject("msg", msg);
  		} else {
  			SampleInputs sampleInputs = new SampleInputs();
  			sampleInputs.setId(id);
			ProductQuantity productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();
  			model.addObject("sampleInputs", sampleInputs);
  			model.addObject("productQuantity", productQuantity);
  			model.addObject("msg", "Dodaj ili oduzmi količinu proizvoda na stanju.");
  			model.setViewName("admin/product_add_remove");
  		}
	model.addObject("product", savedProduct);
  return model;
 }
 
 // Adding product quantity
 @RequestMapping(value= {"/user/add"}, method=RequestMethod.POST)
 public ModelAndView addProduct(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  savedProduct = productRepository.findById(sampleInputs.getId()).get();
  	User user = getCurrentUser();
	  ProductQuantity oldProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();

  if(sampleInputs.getQuantity() == null) {
		SampleInputs inputs = new SampleInputs();
		inputs.setId(savedProduct.getId());
		model.addObject("sampleInputs", inputs);
		model.addObject("product", savedProduct);
		model.addObject("productQuantity", oldProductQuantity);
		model.addObject("msg", "Molim vas unesite količinu artikla u odgovarajuće polje!");
		model.setViewName("admin/product_add_remove");
  	} else {
  	  		int oldQuantity;
  	  try {
  	  		oldQuantity = oldProductQuantity.getQuantity();
  	  } catch(Exception e) {
  	  	  		UserProduct userProduct = new UserProduct(user.getId(), savedProduct.getId());
  	  			ProductQuantity productQuantity = new ProductQuantity(userProduct, 0);
  	  			productQuantityRepository.save(productQuantity);
  	  			oldQuantity = 0;
  	  			}
	  	int newQuantiy = oldQuantity + sampleInputs.getQuantity();
	  	ProductQuantity newProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();
	  	newProductQuantity.setQuantity(newQuantiy);
	  	productQuantityRepository.save(newProductQuantity);
	  	
	  	double total = sampleInputs.getQuantity() * savedProduct.getPrice();
	  	String transType = "ULAZ";
	  	Transaction transaction = new Transaction(new Date(), sampleInputs.getQuantity(), total, transType, savedProduct, user);
	  	transactionRepository.save(transaction);
	  	
		  List <Product> replaceProducts = savedProduct.getProducts();
		  newProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();

		  model.addObject("replaceProducts", replaceProducts);
		  model.addObject("msg", "Pregled profila nakon dodavanja ili oduzimanja određene količine artikla!");
		  model.addObject("product", savedProduct);
		  model.addObject("productQuantity", newProductQuantity);
		  model.setViewName("home/product_profile");
  	}
  return model;
 }
 
 // After back button is clicked return back product profile add and remove
 @RequestMapping(value= {"user/add"}, method=RequestMethod.GET)
 public String backToAddRemove() {
	 Long productId = savedProduct.getId();
	 return "redirect:/home/productprofile/" + productId;
 }
 
 // Subtracting product quantity
 @RequestMapping(value= {"/user/remove"}, method=RequestMethod.POST)
 public ModelAndView removeProduct(@Valid SampleInputs sampleInputs, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  savedProduct = productRepository.findById(sampleInputs.getId()).get();
	User user = getCurrentUser();
	  ProductQuantity oldProductQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), savedProduct.getId())).get();

  if(sampleInputs.getQuantity() == null) {
		SampleInputs inputs = new SampleInputs();
		inputs.setId(savedProduct.getId());
		model.addObject("sampleInputs", inputs);
		  model.addObject("productQuantity", oldProductQuantity);
		model.addObject("msg", "Molim vas unesite količinu artikla u odgovarajuće polje!");
		model.setViewName("admin/product_add_remove");
  	}  else if(oldProductQuantity.getQuantity() <= 0 || oldProductQuantity.getQuantity() - sampleInputs.getQuantity() < 0){
  			SampleInputs inputs = new SampleInputs();
  			inputs.setId(savedProduct.getId());
  			model.addObject("sampleInputs", inputs);
  			List <Product> replaceProducts = savedProduct.getProducts();
  			model.addObject("replaceProducts", replaceProducts);
  			model.addObject("productQuantity", oldProductQuantity);
  			model.addObject("err", "Nema proizvoda na stanju ili količina na stanju nije dovoljna za transakciju!");
  			model.setViewName("admin/product_add_remove");
  		} else {
  			int oldQuantity = oldProductQuantity.getQuantity();
  			int newQuantiy = oldQuantity - sampleInputs.getQuantity();
  			oldProductQuantity.setQuantity(newQuantiy);
  		  	productQuantityRepository.save(oldProductQuantity);
  			
  		  	double total = sampleInputs.getQuantity() * savedProduct.getPrice();
  		  	String transType = "IZLAZ";
  		  	Transaction transaction = new Transaction(new Date(), sampleInputs.getQuantity(), total, transType, savedProduct, user);
  		  	transactionRepository.save(transaction);
  	
  			List <Product> replaceProducts = savedProduct.getProducts();
  			
  		  model.addObject("productQuantity", oldProductQuantity);
  		  model.addObject("replaceProducts", replaceProducts);
  			model.addObject("msg", "Pregled profila nakon dodavanja ili oduzimanja određene količine artikla!");
  			model.setViewName("home/product_profile");
  		}
	model.addObject("product", savedProduct);
  	return model;
 }
 
 // After back button is clicked return back product profile add and remove
 @RequestMapping(value= {"user/remove"}, method=RequestMethod.GET)
 public String backToRemoveAdd() {
	 Long productId = savedProduct.getId();
	 return "redirect:/home/productprofile/" + productId;
 }
 
 // Creating car brand
 @RequestMapping(value= {"admin/createbrand"}, method=RequestMethod.GET)
 public ModelAndView createBrand() {
  ModelAndView model = new ModelAndView();
  Brand brand = new Brand();
  model.addObject("brand", brand);
  model.setViewName("admin/create_brand");
  
  return model;
 }
 
 // Entering car brand name
 @RequestMapping(value= {"admin/createbrand"}, method=RequestMethod.POST)
 public ModelAndView createBrand(@Valid Brand brand, BindingResult bindingResult) {
  ModelAndView model = new ModelAndView();
  Brand brandExists = brandRepository.findByName(brand.getName());
  
  if(brandExists != null) {
   bindingResult.rejectValue("name", "error.name", "Ova marka automobila već postoji!");
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
 
 // Creating car model GET
 @RequestMapping(value= {"admin/createcarmodel"}, method=RequestMethod.GET)
 public ModelAndView createCarModel() {
  ModelAndView model = new ModelAndView();
  CarModel carModel = new CarModel();
  Brand brand = brandRepository.findById(1L).get();
  List<Brand> brands = (List<Brand>) brandRepository.findAll();
  model.addObject("brand", brand);
  model.addObject("carModel", carModel);
  model.addObject("brands", brands);
  model.setViewName("admin/create_car_model");
  
  return model;
 }
 
 // Creating car model POST
 @RequestMapping(value= {"admin/createcarmodel"}, method=RequestMethod.POST)
 public ModelAndView createCarModel(@Valid CarModel carModel) {
  ModelAndView model = new ModelAndView();
  CarModel carModelExists = carModelRepository.findByName(carModel.getName());
  if(carModelExists != null) {
	  model.addObject("err", "Ovaj model automobila već postoji!");
  } else {
	  carModelRepository.save(carModel);
	  model.addObject("msg", "Novi model automobila je uspješno kreiran!");
  }
  model.addObject("carModel", carModel);
  List<Brand> brands = (List<Brand>) brandRepository.findAll();
  model.addObject("brands", brands);
  model.setViewName("admin/create_car_model");
  return model;
 }
 
 // Identification of user for different purposes
 private User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		return user;
}

 // Adding new created product to existing users
 private void newProductAddedToExistingUsers(Product newProduct, List<User> usersList) {
	 List<ProductQuantity> productQuantitiyList = new ArrayList<ProductQuantity>();
	 for(User user: usersList) {
		 ProductQuantity productQuantity;
		 try {
			 productQuantity = productQuantityRepository.findById(new UserProduct(user.getId(), newProduct.getId())).get();
		 } catch(Exception e) {
			 productQuantity = new ProductQuantity(new UserProduct(user.getId(), newProduct.getId()), 0);
			 productQuantityRepository.save(productQuantity);
		 }
		 productQuantitiyList.add(productQuantity);
	 }
 }
}