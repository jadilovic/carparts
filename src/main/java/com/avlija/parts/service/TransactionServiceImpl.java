package com.avlija.parts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.avlija.parts.model.Transaction;
import com.avlija.parts.repository.TransactionRepository;

/*
 * UserController line 320
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Page<Transaction> getPaginatedTransactions(Pageable pageable) {
		return transactionRepository.findAll(pageable);
	}

}
