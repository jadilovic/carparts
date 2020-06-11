package com.avlija.parts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
    List<Transaction> findByProduct(Product product);
    
    List<Transaction> findByUser(User user);
    
    List<Transaction> findByProductOrderByCreatedDesc(Product product);
    
    List<Transaction> findByOrderByCreatedDesc(PageRequest pageRequest);

	List<Transaction> findByOrderByCreatedDesc();
	
	Page<Transaction> findAll(Pageable pageable);
	
	Page<Transaction> findByUser(User user, Pageable pageable);

	List<Transaction> findFirst30ByProductOrderByCreatedDesc(Product product);
	
	List<Transaction> findFirst30ByProductAndUserOrderByCreatedDesc(Product product, User user);

}
