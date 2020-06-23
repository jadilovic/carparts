package com.avlija.parts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
 
 User findByEmail(String email);
 
 //Optional<User> findByEmail(String email);
 User findByResetToken(String resetToken);
}