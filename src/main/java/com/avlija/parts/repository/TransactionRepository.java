package com.avlija.parts.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avlija.parts.model.Product;
import com.avlija.parts.model.Transaction;
import com.avlija.parts.model.User;

@Repository("transactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    
    Product findByProduct(Product product);
    
    User findByUser(User user);
}
