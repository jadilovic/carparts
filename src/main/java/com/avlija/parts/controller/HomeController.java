package com.avlija.parts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
	
// Display of client services
 @RequestMapping(value= {"/clientservices"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/clientServices");
  return model;
 }
 
 // Instructions and description of how to become a client
 @RequestMapping(value= {"/becomeclient"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/becomeClient");
  
  return model;
 }
}
