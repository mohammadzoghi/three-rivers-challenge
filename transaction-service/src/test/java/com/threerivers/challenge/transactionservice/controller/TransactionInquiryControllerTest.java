package com.threerivers.challenge.transactionservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryRequest;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryResponse;
import com.threerivers.challenge.transactionservice.model.TransactionStreamRequest;
import com.threerivers.challenge.transactionservice.model.TransactionType;
import com.threerivers.challenge.transactionservice.service.TransactionInquiryService;

@WebMvcTest(TransactionInquiryController.class)
public class TransactionInquiryControllerTest {

	@Autowired
	MockMvc mockMVC;

	@MockBean
	private TransactionInquiryService transactionInquiryService;

	private static LocalDateTime start;
	private static LocalDateTime end;
	private static List<Transaction> transactions;
	private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	
	@BeforeAll
	public static void init() {
		start = LocalDateTime.now();
		end = LocalDateTime.now();
		Transaction t1 = new Transaction();
		Transaction t2 = new Transaction();
		transactions = applyTransactionFields(start, end, t1, t2);
	}
	
	@Test
	public void transactionEndpointMustCallGetTransactions() throws Exception {
		
		TransactionInquiryRequest request = new TransactionInquiryRequest("123", start, end, null, 0, 10);
		
		TransactionInquiryResponse response = new TransactionInquiryResponse(transactions, 12, 2);
		String jsonResponse = new ObjectMapper().writeValueAsString(response);
		when(transactionInquiryService.getTransactions(any(TransactionInquiryRequest.class))).thenReturn(response);
		mockMVC.perform(get("/transactions").param("accountNumber", request.getAccountNumber())
				.param("start", formatter.format(request.getStart())).param("end", formatter.format(request.getEnd()))
				.param("pageSize", String.valueOf(request.getPageSize()))
				.param("pageNumber", String.valueOf(request.getPageNumber())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(jsonResponse, false));
		verify(transactionInquiryService, times(1)).getTransactions(eq(request));
	}

	@Test
	public void transactionStramEndpointMustCallGetTransactionsStream() throws Exception {

		TransactionStreamRequest request = new TransactionStreamRequest("123", start, end, TransactionType.DEPOSIT);

		String jsonResponse = new ObjectMapper().writeValueAsString(transactions);

		when(transactionInquiryService.getTransactionsStream(any(TransactionStreamRequest.class)))
				.thenReturn(transactions.stream());

		mockMVC.perform(get("/transactions-stream").param("accountNumber", request.getAccountNumber())
				.param("start", formatter.format(request.getStart())).param("end", formatter.format(request.getEnd()))
				.param("type", request.getType().name()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().json(jsonResponse, false));
		verify(transactionInquiryService, times(1)).getTransactionsStream(eq(request));
	}

	@Test
	public void transactionsEndpointMustRetrunBadRequestForInvalidInput() throws Exception {
		
		TransactionInquiryRequest request = new TransactionInquiryRequest(null, start, end, null, 0, 10);
		
		mockMVC.perform(get("/transactions").param("accountNumber", request.getAccountNumber())
				.param("start", formatter.format(request.getStart())).param("end", formatter.format(request.getEnd()))
				.param("pageSize", String.valueOf(request.getPageSize()))
				.param("pageNumber", String.valueOf(request.getPageNumber())).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void transactionsStreamEndpointMustRetrunBadRequestForInvalidInput() throws Exception {
		
		TransactionStreamRequest request = new TransactionStreamRequest(null, start, end, null);
		
		mockMVC.perform(get("/transactions-stream").param("accountNumber", request.getAccountNumber())
				.param("start", formatter.format(request.getStart())).param("end", formatter.format(request.getEnd()))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
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
