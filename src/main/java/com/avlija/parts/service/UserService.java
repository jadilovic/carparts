package com.avlija.parts.service;

import java.util.List;
import com.avlija.parts.model.User;

public interface UserService {
  
	// UserController lines 82, 117, 185, 219 and 461
	// PasswordController line 48
	// SearchController line 279
	// Market Controller 318 and AdminController 451
 public User findUserByEmail(String email);
 
 // UserController 82 and 117
 public void saveUser(User user);
 
 // UserController line 376
 public void updateUser(User user);
 
 // PasswordController lines 48 and 107
 public void resetUpdate(User user);
 
 // UserController line 350
 public List<User> findAllUsers();
 
 // UserRepository - PasswordController lines 89 and 107
 public User findUserByResetToken(String resetToken);
 
}