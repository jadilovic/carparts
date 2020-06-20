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
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {
 
 @Autowired
 private UserRepository userRepository;
 
 @Autowired
 private RoleRespository roleRespository;
 
 @Autowired
 private BCryptPasswordEncoder bCryptPasswordEncoder;

 @Override
 public User findUserByEmail(String email) {
  return userRepository.findByEmail(email);
 }

 @Override
 public void saveUser(User user) {
  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
  user.setActive(1);
  Role userRole = roleRespository.findByRole(user.getRole());
  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
  userRepository.save(user);
 }

@Override
public List<User> findAllUsers() {
	return userRepository.findAll();
}

@Override
public void updateUser(User user) {
	  Role userRole = roleRespository.findByRole(user.getRole());
	  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
}

@Override
public Optional findUserByResetToken(String resetToken) {
	return userRepository.findByResetToken(resetToken);
}

}