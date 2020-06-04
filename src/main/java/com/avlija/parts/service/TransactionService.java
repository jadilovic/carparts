package com.avlija.parts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.avlija.parts.model.Transaction;

public interface TransactionService {

	public Page<Transaction> getPaginatedTransactions(Pageable pageable);
}
