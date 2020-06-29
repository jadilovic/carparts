package com.avlija.parts.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Request;
import com.avlija.parts.model.User;

@Repository("requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {
 
	Page<Request> findByUser(User user, Pageable pageable);


}