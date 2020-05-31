package com.avlija.parts.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
    List<Transaction> findByProduct(Product product);
    
    List<Transaction> findByUser(User user);
}
