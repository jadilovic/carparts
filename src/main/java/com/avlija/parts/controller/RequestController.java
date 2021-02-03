package com.avlija.parts.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.model.Request;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.RequestRepository;
import com.avlija.parts.service.UserService;

@Controller
public class RequestController {
	 
	 @Autowired
	 private RequestRepository requestRepository;
	 
	 @Autowired
	 private UserService userService;
 
	 // Staring request for adding new auto parts sifras
	 @RequestMapping(value= {"/user/request"}, method=RequestMethod.GET)
	 public ModelAndView requestSifra() {
	  ModelAndView model = new ModelAndView();
	  Request request = new Request();
	  model.addObject("request", request);
	  model.addObject("msg", "Zahtjev za dopunu baze sa novim siframa. Unesite sifre u slobodna polja!");
	  model.setViewName("user/request_sifra");
	  return model;
	 }
	 
	 // Submitting the sifra values
	 @RequestMapping(value= {"/user/request"}, method=RequestMethod.POST)
	 public String publishPost(@Valid Request newRequest) {
		  User user = getCurrentUser();
		  
	  	   newRequest.setCreated(new Date());
	  	   newRequest.setCompleted(0);
	  	   newRequest.setUser(user);
	  	   requestRepository.save(newRequest);
	  	   Long id = newRequest.getId();
	  	   // Redirecting saved sifra values of the request to display request info line 60
	  	   return "redirect:/user/requestinfo/" + id;
	 }
	 
	 // Displaying the info about the request containing new sifras
	 @RequestMapping(value= {"/user/requestinfo/{id}"}, method=RequestMethod.GET)
	 public ModelAndView requestInfo(@PathVariable(name = "id") Long id)  {
		 Request request = requestRepository.findById(id).get();
		 ModelAndView model = new ModelAndView();
	   		
		 model.addObject("msg", "Info o poslanom zahtjevu za dopunu sifri");
		 model.addObject("request", request);
		 model.setViewName("user/request_info");
	  	   return model;
	 }
	 
	 // Changing the status of the request if new sifras were added or not
	 @RequestMapping(value= {"/admin/requestcompleted/{id}"}, method=RequestMethod.GET)
	 public ModelAndView changingCompletedStatusOfRequest(@PathVariable(name = "id") Long id)  {
		 ModelAndView model = new ModelAndView();
		 Request request = requestRepository.findById(id).get();
		 if(request.getCompleted() == 0) {
			 request.setCompleted(1);
			 model.addObject("msg", "Zahtjev za dopunu sifri u bazi je izvršen");
		 } else {
			 request.setCompleted(0);
			 model.addObject("err", "Zahtjev za dopunu sifri u bazi na čekanju");
		 }
		 requestRepository.save(request);
		 model.addObject("request", request);
		 model.setViewName("user/request_info");
	  	   return model;
	 }
	 
	 // Display of requests for new sifras by the user
	 @GetMapping(value= {"/user/userrequests"})
	 public ModelAndView listUserRequests(HttpServletRequest request) {
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
	       Page <Request> requestsList = null;
	   		requestsList = requestRepository.findByUser(user, PageRequest.of(page, size, Sort.by("created").descending()));

	   		String message = null;
	   		if(requestsList == null) {
	   			message = "Nemate objavljenih oglasa";
	   		}
	   		
	   		model.addObject("message", message);
	  	   model.addObject("requestsList", requestsList);
	  	   model.setViewName("user/user_requests");
	  	   return model;
	 }
	 
	 // List of all requests for new sifras / Admin only access
	 @GetMapping(value= {"/admin/allrequests"})
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

	       Page <Request> requestsList = null;
	   		requestsList = requestRepository.findAll(PageRequest.of(page, size, Sort.by("created").descending()));

	   		String message = null;
	   		if(requestsList == null) {
	   			message = "Nema zahtjeva za dopunu sifri";
	   		}
	   		
	   		model.addObject("message", message);
	  	   model.addObject("requestsList", requestsList);
	  	   model.setViewName("admin/all_requests");
	  	   return model;
	 }
	 
	 // Getting current user for different purposes
	 private User getCurrentUser() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findUserByEmail(auth.getName());
			return user;
	}
}
