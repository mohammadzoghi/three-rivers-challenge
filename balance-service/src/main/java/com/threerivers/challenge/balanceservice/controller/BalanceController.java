package com.threerivers.challenge.balanceservice.controller;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.threerivers.challenge.balanceservice.model.Balance;
import com.threerivers.challenge.balanceservice.service.BalanceService;

import io.swagger.v3.oas.annotations.Operation;


@RestController()
@RequestMapping("/")
public class BalanceController {
	private static final Logger logger = LoggerFactory.getLogger(BalanceController.class);

	BalanceService balanceService;
	
	@Operation(tags = {"Get Transactions"}, summary = "This end point returns balance and update time for the given account number")
	@RequestMapping(path = "balance/{accountNumber}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getBalance(@PathVariable String accountNumber) {
		
		logger.info("Request received: {}", accountNumber);
		
		Balance balance =  balanceService.getBalance(accountNumber);
		
		if(Objects.nonNull(balance)) {
			return ResponseEntity.status(HttpStatus.OK).body(balance);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Account with number: [%s] not found", accountNumber));

	}

	public BalanceController(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

}
