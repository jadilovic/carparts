package com.avlija.parts.service;

import java.util.List;

import com.avlija.parts.model.User;

public interface UserService {
  
 public User findUserByEmail(String email);
 
 public void saveUser(User user);
 
 public List<User> findAllUsers();
}