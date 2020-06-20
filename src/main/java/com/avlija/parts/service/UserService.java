package com.avlija.parts.service;

import java.util.List;
import java.util.Optional;

import com.avlija.parts.model.User;

public interface UserService {
  
 public User findUserByEmail(String email);
 
 public void saveUser(User user);
 
 public void updateUser(User user);
 
 public List<User> findAllUsers();
 
 // public Optional<User> findUserByEmail(String email);
 public Optional<User> findUserByResetToken(String resetToken);
 // public void save(User user);
}