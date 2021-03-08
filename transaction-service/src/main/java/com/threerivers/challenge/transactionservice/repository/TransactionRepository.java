package com.threerivers.challenge.transactionservice.repository;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.model.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	Page<Transaction> findAllByAccountNumberAndTypeAndTransactionTsBetween(String accountNumber, TransactionType type, LocalDateTime start, LocalDateTime end, Pageable pageable);

	Page<Transaction> findAllByAccountNumberAndTransactionTsBetween(String accountNumber, LocalDateTime start, LocalDateTime end, Pageable pageable);

	Stream<Transaction> streamAllByAccountNumberAndTypeAndTransactionTsBetween(String accountNumber, TransactionType type, LocalDateTime start, LocalDateTime end);

	Stream<Transaction> streamAllByAccountNumberAndTransactionTsBetween(String accountNumber, LocalDateTime start, LocalDateTime end);

}
