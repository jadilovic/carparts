package com.avlija.parts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.avlija.parts.model.Role;
import com.avlija.parts.model.User;
import com.avlija.parts.repository.RoleRespository;
import com.avlija.parts.repository.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
 
 @Autowired
 private UserRepository userRepository;
 
 @Autowired
 private RoleRespository roleRespository;
 
 @Autowired
 private BCryptPasswordEncoder bCryptPasswordEncoder;

 // PasswordController line 48
 // UserController lines 82, 117, 185, 219 and 361
 @Override
 public User findUserByEmail(String email) {
  return userRepository.findByEmail(email);
 }

//UserController 82 and 117
 @Override
 public void saveUser(User user) {
  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
  user.setActive(1);
  Role userRole = roleRespository.findByRole(user.getRole());
  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
  userRepository.save(user);
 }

 // UserController line 350
@Override
public List<User> findAllUsers() {
	return userRepository.findAll();
}

//UserController line 376
@Override
public void updateUser(User user) {
	  Role userRole = roleRespository.findByRole(user.getRole());
	  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
}

// UserRepository
// PasswordController lines 89 and 107
@Override
public
User findUserByResetToken(String resetToken) {
	return userRepository.findByResetToken(resetToken);
}

// PasswordController
@Override
public void resetUpdate(User user) {
	  userRepository.save(user);
}

}