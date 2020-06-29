package com.avlija.parts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Request;

@Repository("requestRepository")
public interface RequestRepository extends JpaRepository<Request, Long> {
 

}