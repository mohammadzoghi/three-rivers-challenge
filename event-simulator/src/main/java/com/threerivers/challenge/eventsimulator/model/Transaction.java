package com.threerivers.challenge.eventsimulator.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
	private String accountNumber;
	private LocalDateTime transactionTs;
	private TransactionType type;
	private BigDecimal amount;
	
	public Transaction(String accountNumber, TransactionType type, BigDecimal amount) {
		super();
		this.accountNumber = accountNumber;
		this.type = type;
		this.amount = amount;
		this.transactionTs = LocalDateTime.now();
}
	
	public Transaction() {
		super();
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public LocalDateTime getTransactionTs() {
		return transactionTs;
	}
	public void setTransactionTs(LocalDateTime transactionTs) {
		this.transactionTs = transactionTs;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
