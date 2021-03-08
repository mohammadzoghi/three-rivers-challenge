package com.threerivers.challenge.transactionservice.service;

import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryRequest;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryResponse;
import com.threerivers.challenge.transactionservice.model.TransactionStreamRequest;
import com.threerivers.challenge.transactionservice.repository.TransactionRepository;

@Service
public class TransactionInquiryService {

	private TransactionRepository transactionRepository;

	public TransactionInquiryResponse getTransactions(TransactionInquiryRequest request) {
		Page<Transaction> transactionsPage;
		Pageable pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("transactionTs"));
		if (request.getType() == null) {
			transactionsPage = transactionRepository.findAllByAccountNumberAndTransactionTsBetween(
					request.getAccountNumber(), request.getStart(), request.getEnd(), pageRequest);
		} else {
			transactionsPage = transactionRepository.findAllByAccountNumberAndTypeAndTransactionTsBetween(
					request.getAccountNumber(), request.getType(), request.getStart(), request.getEnd(), pageRequest);
		}

		TransactionInquiryResponse response = new TransactionInquiryResponse(transactionsPage.toList(),
				transactionsPage.getTotalElements(), transactionsPage.getTotalPages());

		return response;

	}
	
	public Stream<Transaction> getTransactionsStream(TransactionStreamRequest request) {
		if (request.getType() == null) {
			return transactionRepository.streamAllByAccountNumberAndTransactionTsBetween(
					request.getAccountNumber(), request.getStart(), request.getEnd());
		} else {
			return transactionRepository.streamAllByAccountNumberAndTypeAndTransactionTsBetween(
					request.getAccountNumber(), request.getType(), request.getStart(), request.getEnd());
		}

	}

	public TransactionInquiryService(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}

}
