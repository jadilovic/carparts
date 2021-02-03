package com.avlija.parts.repository;

/*
 * Used in AdminController and UserController 
 */
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
 
	// UserController line 320
    List<Transaction> findByUser(User user);
	
    // UserController line 320
	Page<Transaction> findAll(Pageable pageable);
	
	// UserController line 320
	Page<Transaction> findByUser(User user, Pageable pageable);

	// UserController line 291
	List<Transaction> findFirst30ByProductOrderByCreatedDesc(Product product);
	
	// UserController line 291
	List<Transaction> findFirst30ByProductAndUserOrderByCreatedDesc(Product product, User user);

}
