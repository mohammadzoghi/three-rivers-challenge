package com.threerivers.challenge.transactionservice.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threerivers.challenge.transactionservice.model.Transaction;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryRequest;
import com.threerivers.challenge.transactionservice.model.TransactionInquiryResponse;
import com.threerivers.challenge.transactionservice.model.TransactionStreamRequest;
import com.threerivers.challenge.transactionservice.model.TransactionType;
import com.threerivers.challenge.transactionservice.service.TransactionInquiryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/")
public class TransactionInquiryController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionInquiryController.class);

	private TransactionInquiryService transactionInquiryService;

	public TransactionInquiryController(TransactionInquiryService transactionInquiryService) {
		super();
		this.transactionInquiryService = transactionInquiryService;
	}

	@Operation(tags = {
			"Get Transactions" }, summary = "This end point returns transactions for accountNumber between start and end dates. Page number and size are for pagination. type (DEPOSIT, WITHDRAW) is optional")
	@RequestMapping(path = "transactions", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody TransactionInquiryResponse getTransactions(@RequestParam String accountNumber,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
			@RequestParam(required = false) TransactionType type, @RequestParam int pageNumber,
			@RequestParam int pageSize) {

		TransactionInquiryRequest request = new TransactionInquiryRequest(accountNumber, start, end, type, pageNumber,
				pageSize);

		logger.info("Request received: {}", request);

		return transactionInquiryService.getTransactions(request);
	}

	@Operation(tags = {
			"Get Transactions steam" }, summary = "This end point streams all the transactions for accountNumber between start and end dates. type (DEPOSIT, WITHDRAW) is optional")
	@RequestMapping(path = "transactions-stream", method = RequestMethod.GET, produces = "application/stream+json")
	@Transactional(readOnly = true)
	public void streamTransactions(@RequestParam String accountNumber,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
			@RequestParam(required = false) TransactionType type, HttpServletResponse response) {
		TransactionStreamRequest request = new TransactionStreamRequest(accountNumber, start, end, type);
		logger.info("Request received: {}", request);

		try (Stream<Transaction> transactionStream = transactionInquiryService.getTransactionsStream(request)) {
			final Writer writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			new ObjectMapper().writerFor(Iterator.class).writeValue(writer, transactionStream.iterator());

		} catch (IOException e) {
			logger.error("Unexpected exception occurred: ", e);
			response.setStatus(500);

		}
	}

}
