package com.avlija.parts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avlija.parts.model.Transaction;

public interface TransactionService {
	/*
	 * UserController line 320
	 */

	public Page<Transaction> getPaginatedTransactions(Pageable pageable);
}
