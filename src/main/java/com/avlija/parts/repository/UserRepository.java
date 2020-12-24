package com.avlija.parts.repository;

/*
 * Used in AdminController and UserController
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
 
	// UserController lines 257 and 376
 User findByEmail(String email);
 
 // UserServiceImpl line 53
 User findByResetToken(String resetToken);
}