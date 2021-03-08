package com.threerivers.challenge.transactionservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryRequest;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryResponse;
import com.threerivers.challenge.transactionservice.model.TransactionStreamRequest;
import com.threerivers.challenge.transactionservice.model.TransactionType;
import com.threerivers.challenge.transactionservice.repository.TransactionRepository;

@SpringBootTest
public class TransactionInquiryServiceTest {

	@Autowired
	private TransactionInquiryService transactionInquiryService;

	@MockBean
	private TransactionRepository transactionRepository;


	private static LocalDateTime start;
	private static LocalDateTime end;
	private static List<Transaction> transactions;
	
	@BeforeAll
	public static void init() {
		start = LocalDateTime.now();
		end = LocalDateTime.now();

		Transaction t1 = new Transaction();
		Transaction t2 = new Transaction();
		transactions = applyTransactionFields(start, end, t1, t2);
	}
	
	@Test
	public void getTransactionMustCallQueryWithTypeWhenInputHasType() {
		

		TransactionInquiryRequest request = new TransactionInquiryRequest("123", start, end, TransactionType.DEPOSIT, 0,
				10);
		Page<Transaction> page = new PageImpl<>(transactions,
				PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("transactionTs")), 2);

		when(transactionRepository.findAllByAccountNumberAndTypeAndTransactionTsBetween(any(String.class),
				any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
						.thenReturn(page);

		TransactionInquiryResponse response = transactionInquiryService.getTransactions(request);
		assertTrue(response.getTransactions().containsAll(transactions));
		assertEquals(response.getCount(), 2);
		assertEquals(response.getPageCount(), 1);
		verify(transactionRepository, times(1)).findAllByAccountNumberAndTypeAndTransactionTsBetween(eq("123"),
				eq(TransactionType.DEPOSIT), eq(start), eq(end), any(Pageable.class));

	}

	@Test
	public void getTransactionMustCallQueryWithoutTypeWhenInputTypeIsNull() {

		TransactionInquiryRequest request = new TransactionInquiryRequest("123", start, end, null, 0, 10);
		Page<Transaction> page = new PageImpl<>(transactions,
				PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by("transactionTs")), 2);

		when(transactionRepository.findAllByAccountNumberAndTransactionTsBetween(any(String.class),
				any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class))).thenReturn(page);

		TransactionInquiryResponse response = transactionInquiryService.getTransactions(request);
		assertTrue(response.getTransactions().containsAll(transactions));
		assertEquals(response.getCount(), 2);
		assertEquals(response.getPageCount(), 1);
		verify(transactionRepository, times(1)).findAllByAccountNumberAndTransactionTsBetween(eq("123"), eq(start),
				eq(end), any(Pageable.class));

	}

	@Test
	public void stramTransactionMustCallQueryWithTypeWhenInputHasType() {

		TransactionStreamRequest request = new TransactionStreamRequest("123", start, end, TransactionType.DEPOSIT);


		when(transactionRepository.streamAllByAccountNumberAndTypeAndTransactionTsBetween(any(String.class),
				any(TransactionType.class), any(LocalDateTime.class), any(LocalDateTime.class)))
						.thenReturn(transactions.stream());

		Stream<Transaction> response = transactionInquiryService.getTransactionsStream(request);

		response.forEach(transaction -> assertTrue(transactions.contains(transaction)));
		verify(transactionRepository, times(1)).streamAllByAccountNumberAndTypeAndTransactionTsBetween("123",
				TransactionType.DEPOSIT, start, end);

	}

	@Test
	public void stramTransactionMustCallQueryWithoutTypeWhenInputTypeIsNull() {
		TransactionStreamRequest request = new TransactionStreamRequest("123", start, end, null);


		when(transactionRepository.streamAllByAccountNumberAndTransactionTsBetween(any(String.class),
				any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions.stream());

		Stream<Transaction> response = transactionInquiryService.getTransactionsStream(request);

		response.forEach(transaction -> assertTrue(transactions.contains(transaction)));
		verify(transactionRepository, times(1)).streamAllByAccountNumberAndTransactionTsBetween("123", start, end);

	}

	private static List<Transaction> applyTransactionFields(LocalDateTime start, LocalDateTime end, Transaction t1,
			Transaction t2) {
		t1.setAccountNumber("123");
		t1.setTransactionTs(start);
		t1.setType(TransactionType.DEPOSIT);
		t1.setAmount(new BigDecimal(100));
		t2.setAccountNumber("123");
		t2.setTransactionTs(end);
		t2.setType(TransactionType.WITHDRAW);
		t2.setAmount(new BigDecimal(100));
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(t1);
		transactions.add(t2);
		return transactions;
	}
}
