package com.avlija.parts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.avlija.parts.model.User;

@Controller
public class HomeController {
 
 @RequestMapping(value= {"/home/clientservices"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/clientServices");
  return model;
 }
 
 @RequestMapping(value= {"/home/becomeclient"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  model.setViewName("home/becomeClient");
  
  return model;
 }
}
